package Scenario

import Scripts._
import io.gatling.core.Predef._
import io.gatling.core.structure.PopulationBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.Scripts._
import scala.collection.mutable.ListBuffer
import scala.concurrent.duration.DurationInt


class OpenModel_Test extends Simulation {

  val main_header: Map[String, String] = Map(
    "Connection" -> "Keep-Alive"
    //...
  )

  val httpConf: HttpProtocolBuilder = http.baseUrls("http://localhost:443").headers(main_header)
  //.proxy(Proxy("localhost:8080",8080))

  val populationOpen = new ListBuffer[PopulationBuilder]
  val populationClose = new ListBuffer[PopulationBuilder]


  //Если у вас не целый РПС использовать отдельный лист для упрощения
  val listOpen = List(
    new UC_TestClass_1(10.4, 10, 0),
    new UC_TestClass_2(4.8, 10, 0)
    //другие скрипты ...
  )

  val listClose = List(
    new UC_TestClass_1(10, 10, 0),
    new UC_TestClass_2(4, 10, 0)
  )
  //  Для открытой модели
  for (element <- listOpen) {
    populationOpen += element.scn_open.inject(
      rampUsersPerSec(1).to(element.Users * 1).during(5.minutes),
      constantUsersPerSec(element.Users * 1).during(15.minutes).randomized,
      rampUsersPerSec(element.Users * 1).to(element.Users * 1.2).during(5.minutes),
      constantUsersPerSec(element.Users * 1.2).during(15.minutes).randomized,

    ).protocols(httpConf)
  }

  // Для закрытой модели
  for (element <- listClose){
    populationClose += element.scn_open.inject(
      rampConcurrentUsers(0).to((element.Users*1).toInt).during(5.minutes),
      constantConcurrentUsers((element.Users*1).toInt).during(15.minutes),
      rampConcurrentUsers((element.Users*1).toInt).to((element.Users * 1.2).toInt).during(5.minutes),
      constantConcurrentUsers((element.Users * 1.2).toInt).during(15.minutes),
    )
  }




  //setUp(populationOpen.toList).maxDuration(120.minutes)
  setUp(populationClose.toList).maxDuration(120.minutes)


}

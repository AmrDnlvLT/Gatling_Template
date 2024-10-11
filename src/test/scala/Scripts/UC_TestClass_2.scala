package scala.Scripts

import Traits.Trait
import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._

class UC_TestClass_2(val users: Double, val pacing_lb: Int, val pacing_rb: Int) extends Trait {

  override val Users: Double = users
  override val Pacing_lb: Int = pacing_lb
  override val Pacing_rb: Int = pacing_rb


  //Закрытая модель
  override val scn_close:ScenarioBuilder = scenario("Sceanrio_2")
    .forever {
      pace(Pacing_lb)
        .exec(request())
    }

  //Закрытая модель - pacing  в диапазоне
  override val scn_close_rnd:ScenarioBuilder = scenario("Scneario_2")
    .forever {
      pace(randomRange(Pacing_lb,Pacing_rb))
        .exec(request())
    }

  //Открытая модель
  override val scn_open: ScenarioBuilder = scenario("Sceanrio_2").exec(request())


  def request(): ChainBuilder = {
    exitBlockOnFail(
      exec(http("ScriptName_2")
        .get("/api/v2/")
        .header("Authorization", "Bearer token")
        .check(status in(200, 201))
      )
    )
  }
}

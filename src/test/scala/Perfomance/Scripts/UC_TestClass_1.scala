package Scripts

import Traits.Trait
import com.sun.net.httpserver.Filter.Chain
import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._

class UC_TestClass_1(val users: Double, val pacing_lb: Int, val pacing_rb: Int) extends Trait {

  override val Users: Double = users
  override val Pacing_lb: Int = pacing_lb
  override val Pacing_rb: Int = pacing_rb


  //Закрытая модель
  override val scn_close:ScenarioBuilder = scenario("Scenario_1")
    .forever {
      pace(Pacing_lb)
        .exec(request())
    }

  //Закрытая модель - pacing  в диапазоне
  override val scn_close_rnd:ScenarioBuilder = scenario("Scenario_1")
    .forever {
      pace(randomRange(Pacing_lb,Pacing_rb))
        .exec(request())
    }

  //Открытая модель
  override val scn_open: ScenarioBuilder = scenario("Scenario_1").exec(request())


  def request(): ChainBuilder = {
    exitBlockOnFail(
      exec(http("ScriptName_1")
        .post("/auth")
        //.header("Authorization", "Bearer token")
        .body(StringBody("{\"username\" : \"admin\",\n    \"password\" : \"password123\"}")).asJson
        .check(status in(200, 201))
        .check(jsonPath("$.token").notNull)
      )
    )
  }
}

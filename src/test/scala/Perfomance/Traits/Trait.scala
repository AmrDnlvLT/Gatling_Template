package Traits

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder

import java.util.Random



trait Trait {

  val scn_close: ScenarioBuilder = null
  val scn_close_rnd: ScenarioBuilder = null
  val scn_open: ScenarioBuilder = null

  val Users: Double = 0
  val Pacing_lb: Int = 0
  val Pacing_rb: Int = 0
  // + доп параметры Ex: сколко раз выполниться запрос в repeat

  val rnd = new Random()


  //Функции, фидеры и перменные для вызовв в классах сценария

  def randomRange(lb: Int, rb: Int):Int = {
    //rb + (rb - lb) * Random.nextDouble()
    //lb + Random.nextInt(rb-lb)
    val range = rb -lb +1
    (math.random()*range).toInt + lb
  }

}

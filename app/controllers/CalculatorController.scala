package controllers

import play.api.mvc.{Action, Controller}

class CalculatorController extends Controller {

  def add(i:Int, j:Int) = Action {
    val answer = i+j
    Ok(s"$answer")
  }

  def subtract(i:Int, j:Int) = Action {
    val answer = i-j
    Ok(s"$answer")
  }

  def multiply(i:Int, j:Int) = Action {
    val answer = i*j
    Ok(s"$answer")
  }

  def divide(i:Int, j:Int) = Action {
    Ok(s"${i / j}")
  }

}

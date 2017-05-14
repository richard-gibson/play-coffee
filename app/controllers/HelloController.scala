package controllers

import play.api.mvc.{Action, Controller}

class HelloController extends Controller {

  def hello = Action {
    Ok("hello world")
  }

  def helloName(name:String) = Action {
     Ok(s"hello $name")
  }

  def helloNtimes(n:Int) = Action {
    val output = (1 to n)
                  .map(_ => "hello")
                  .mkString(", ")
    Ok(output)
  }


}

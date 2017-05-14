package controllers

import models.{Todo, TodoModel}
import play.api.libs.json.{Json, OFormat}
import play.api.mvc.{Action, Controller}

class TodoRestController extends Controller {
  implicit val outletAsJson: OFormat[Todo] = Json.format[Todo]

  val todoModel =
    new TodoModel(
      List(Todo(1, "take out rubbish"),
        Todo(2, "cut grass"),
        Todo(3, "stack dishwasher"),
        Todo(4, "paint fence"),
        Todo(5, "clean windows"),
        Todo(6, "clean bathroom")))

  def lookup(task: String) = Action {
    val tasks = todoModel.lookup(task)
      Ok(Json.toJson(tasks))
  }

  def fetchById(id: Int) = Action {
    todoModel.fetchById(id) match {
      case Some(task) => Ok(Json.toJson(task))
      case None => NotFound
    }
  }



}

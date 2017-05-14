package controllers

import javax.inject.Inject

import models.{Todo, TodoModel}
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, Controller, Result}

/**
  * Created by richardgibson on 14/05/2017.
  */
class TodoController
  extends Controller {
  val todoModel =
    new TodoModel(
      List(Todo(1, "take out rubbish"),
        Todo(2, "cut grass"),
        Todo(3, "stack dishwasher"),
        Todo(4, "paint fence"),
        Todo(5, "clean windows"),
        Todo(6, "clean bathroom")))


  def listTodos = Action{
    Ok(views.html.todo.todos(todoModel.fetchAll) )
  }


  def deleteTodoById(id:Int) = Action {
    todoModel.remove(id)
    Redirect(routes.TodoController.listTodos)

  }

}

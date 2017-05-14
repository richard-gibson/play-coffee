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
class TodoController @Inject()(val messagesApi: MessagesApi)
  extends Controller with I18nSupport {
  val todoModel =
    new TodoModel(
      List(Todo(1, "take out rubbish"),
        Todo(2, "cut grass"),
        Todo(3, "stack dishwasher"),
        Todo(4, "paint fence"),
        Todo(5, "clean windows"),
        Todo(6, "clean bathroom")))


  def listTodos = Action{
    Ok(views.html.todo.todos(todoModel.fetchAll, todoForm) )
  }


  def deleteTodoById(id:Int) = Action {
    todoModel.remove(id)
    Redirect(routes.TodoController.listTodos)

  }

  val todoForm: Form[Todo] =
    Form(mapping(
      "id"        -> number,
      "task"     -> nonEmptyText
    )(Todo.apply)(Todo.unapply))


  def submitTodo = Action{ implicit request =>
    val formErrorFn: (Form[Todo]) => Result = {
      errorForm =>
      BadRequest(views.html.todo.todos(todoModel.fetchAll, errorForm))
    }
    val formSuccessFn: Todo => Result = {
      todo =>
        todoModel.upsert(todo)
        Redirect(routes.TodoController.listTodos)
    }

    todoForm.bindFromRequest.fold(formErrorFn, formSuccessFn)
  }
}

package models

class TodoModel(var todos: List[Todo]) {

  def upsert(todo: Todo): Unit = {
    if (todos.exists(_.id == todo.id)) {
      remove(todo.id)
    }
    todos = todo :: todos
  }

  def remove(id:Int): Unit = {
    todos = todos filterNot (_.id == id)
  }

  def lookup(taskFragment: String) =
    todos.filter(_.task contains taskFragment)

  def fetchById(id: Int): Option[Todo] =
    todos.find(_.id == id)

  def fetchAll:List[Todo] = todos

}

case class Todo(id: Int, task: String)
package dmitry.molchanov.mvi_kotlin_app.domain

import dmitry.molchanov.model.TodoItem

/*inline fun TodoItem.update(func: TodoItem.() -> TodoItem): TodoItem = copy(data = data.func())

inline fun Iterable<TodoItem>.update(id: String, func: TodoItem.Data.() -> TodoItem.Data): List<TodoItem> =
    map { if (it.id == id) it.update(func) else it }*/

package dmitry.molchanov.mvi_kotlin_app.domain

interface TodoDatabase {

    fun get(id: String): TodoItem?

    fun create(data: TodoItem.Data): TodoItem

    fun save(id: String, data: TodoItem.Data)

    fun delete(id: String)

    fun getAll(): List<TodoItem>
}

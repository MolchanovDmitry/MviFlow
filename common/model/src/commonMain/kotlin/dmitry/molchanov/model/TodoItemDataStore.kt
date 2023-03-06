package dmitry.molchanov.model

import kotlinx.coroutines.flow.Flow

interface TodoItemDataStore {

    val todoItemsFlow: Flow<List<TodoItem>>

    suspend fun addItem(text: String)

    suspend fun removeItem(itemId: Long)

    suspend fun updateItem(todoItem: TodoItem)
}
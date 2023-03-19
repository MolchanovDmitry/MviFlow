package dmitry.molchanov.database

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import dmitry.molchanov.db.TodoItemTable
import dmitry.molchanov.db.TodoItemTableQueries
import dmitry.molchanov.model.TodoItem
import dmitry.molchanov.model.TodoItemDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoItemDataStoreImpl(private val queries: TodoItemTableQueries) : TodoItemDataStore {

    override val todoItemsFlow: Flow<List<TodoItem>> = queries.selectAll()
        .asFlow()
        .mapToList()
        .map(::getDomainTodoItems)


    override suspend fun addItem(text: String) {
        queries.insert(text = text, isDone = false)
    }

    override suspend fun removeItem(itemId: Long) {
        queries.delete(itemId)
    }

    override suspend fun updateItem(todoItem: TodoItem) {
        queries.update(id = todoItem.id, text = todoItem.text, isDone = todoItem.isDone)
    }

    private fun getDomainTodoItems(todoItemRecords: List<TodoItemTable>): List<TodoItem> {
        return todoItemRecords.map {
            TodoItem(
                id = it.id,
                text = it.text,
                isDone = it.isDone
            )
        }
    }
}
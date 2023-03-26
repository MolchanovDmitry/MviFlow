package dmitry.molchanov.database

import dmitry.molchanov.model.TodoItem
import dmitry.molchanov.model.TodoItemDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class TodoItemDataStoreImpl : TodoItemDataStore {

    override val todoItemsFlow = MutableStateFlow(listOf<TodoItem>())

    override suspend fun addItem(text: String) {
        todoItemsFlow.update {
            it.toMutableList()
                .apply { add(TodoItem(id = Random.nextLong(), text = text, isDone = false )) }
        }
    }

    override suspend fun removeItem(itemId: Long) {
        todoItemsFlow.update {
            it.toMutableList()
                .apply { removeAll { it.id == itemId } }
        }
    }

    override suspend fun updateItem(todoItem: TodoItem) {
        removeItem(todoItem.id)
        todoItemsFlow.update {
            it.toMutableList()
                .apply { add(todoItem) }
        }
    }
}
package dmitry.molchanov.usecase

import dmitry.molchanov.TodoItemDataStore
import dmitry.molchanov.model.TodoItem
import dmitry.molchanov.util.Dispatchers
import kotlinx.coroutines.withContext

class EditTodoItemUseCase(
    private val dispatchers: Dispatchers,
    private val dataStore: TodoItemDataStore
) {

    suspend fun execute(todoItem: TodoItem) = withContext(dispatchers.io){
        dataStore.updateItem(todoItem)
    }
}
package dmitry.molchanov.usecase

import dmitry.molchanov.model.TodoItemDataStore
import dmitry.molchanov.util.Dispatchers
import kotlinx.coroutines.withContext

class RemoveTodoItemUseCase(
    private val dispatchers: Dispatchers,
    private val todoItemDataStore: TodoItemDataStore
) {

    suspend fun execute(itemId: Long) = withContext(dispatchers.io) {
        todoItemDataStore.removeItem(itemId)
    }
}
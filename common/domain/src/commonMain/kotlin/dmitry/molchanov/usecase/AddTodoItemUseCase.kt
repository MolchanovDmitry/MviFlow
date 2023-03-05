package dmitry.molchanov.usecase

import dmitry.molchanov.TodoItemDataStore
import dmitry.molchanov.model.EmptyAddText
import dmitry.molchanov.util.Dispatchers
import kotlinx.coroutines.withContext

class AddTodoItemUseCase(
    private val dispatchers: Dispatchers,
    private val todoItemDataStore: TodoItemDataStore
) {

    suspend fun execute(text: String): Result<Unit> = withContext(dispatchers.io) {
        if (text.isNotEmpty()) {
            todoItemDataStore.addItem(text)
            Result.success(Unit)
        } else {
            Result.failure(EmptyAddText)
        }
    }
}
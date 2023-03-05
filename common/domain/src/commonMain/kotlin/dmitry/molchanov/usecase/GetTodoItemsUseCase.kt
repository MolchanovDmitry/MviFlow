package dmitry.molchanov.usecase

import dmitry.molchanov.TodoItemDataStore
import dmitry.molchanov.model.TodoItem
import dmitry.molchanov.util.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetTodoItemsUseCase(
    private val dispatchers: Dispatchers,
    private val todoItemDataStore: TodoItemDataStore
) {

    fun execute(): Flow<List<TodoItem>> =
        todoItemDataStore.todoItemsFlow
            .flowOn(dispatchers.io)
}
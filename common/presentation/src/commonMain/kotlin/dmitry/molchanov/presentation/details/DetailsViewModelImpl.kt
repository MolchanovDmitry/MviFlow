package dmitry.molchanov.presentation.details

import dmitry.molchanov.model.TodoItem
import dmitry.molchanov.mvi.MviViewModelImpl
import dmitry.molchanov.presentation.details.DetailsStore.*
import dmitry.molchanov.usecase.EditTodoItemUseCase
import dmitry.molchanov.usecase.GetTodoItemsUseCase
import dmitry.molchanov.usecase.RemoveTodoItemUseCase
import dmitry.molchanov.util.Dispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DetailsViewModelImpl(
    dispatchers: Dispatchers,
    reducer: DetailsViewModelReducer,
    getTodoItemsUseCase: GetTodoItemsUseCase,
    private val itemId: Long,
    private val editTodoUseCase: EditTodoItemUseCase,
    private val removeTodoItemUseCase: RemoveTodoItemUseCase,
) : MviViewModelImpl<State, Intent, Message, Nothing>(
    initState = EmptyState,
    reducer = reducer
) {

    private val scope = CoroutineScope(dispatchers.io + SupervisorJob())

    init {
        getTodoItemsUseCase.execute()
            .map { todoItems -> todoItems.find { it.id == itemId } }
            .map { todoItem ->
                val message = todoItem?.let(::SetCurrentItemMsg) ?: FinishMsg
                dispatch(message)
            }
            .launchIn(scope)
    }

    private val currentItem: TodoItem?
        get() = (state.value as? ItemState)?.todoItem

    override fun onIntent(intent: Intent) {
        scope.launch {
            when (intent) {
                DeleteItem ->
                    removeTodoItemUseCase.execute(itemId)

                SwitchDoneFlag ->
                    currentItem?.let { item ->
                        editTodoUseCase.execute(todoItem = item.copy(isDone = !item.isDone))
                    }

                is TextChange ->
                    currentItem?.let { item ->
                        editTodoUseCase.execute(todoItem = item.copy(text = intent.text))
                    }
            }
        }
    }

    override fun clear() {
        scope.cancel()
    }

}
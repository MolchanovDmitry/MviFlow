package dmitry.molchanov.presentation.details

import dmitry.molchanov.model.TodoItem
import dmitry.molchanov.presentation.details.DetailsViewModel.DeleteItem
import dmitry.molchanov.presentation.details.DetailsViewModel.EmptyState
import dmitry.molchanov.presentation.details.DetailsViewModel.FinisState
import dmitry.molchanov.presentation.details.DetailsViewModel.Intent
import dmitry.molchanov.presentation.details.DetailsViewModel.ItemState
import dmitry.molchanov.presentation.details.DetailsViewModel.SwitchDoneFlag
import dmitry.molchanov.presentation.details.DetailsViewModel.TextChange
import dmitry.molchanov.usecase.EditTodoItemUseCase
import dmitry.molchanov.usecase.GetTodoItemsUseCase
import dmitry.molchanov.usecase.RemoveTodoItemUseCase
import dmitry.molchanov.util.Dispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailsViewModelImpl(
    dispatchers: Dispatchers,
    getTodoItemsUseCase: GetTodoItemsUseCase,
    private val itemId: Long,
    private val editTodoUseCase: EditTodoItemUseCase,
    private val removeTodoItemUseCase: RemoveTodoItemUseCase,
) : DetailsViewModel {

    private val scope = CoroutineScope(dispatchers.io + SupervisorJob())

    override val state = getTodoItemsUseCase.execute()
        .map { todoItems -> todoItems.find { it.id == itemId } }
        .map { todoItem -> todoItem?.let(::ItemState) ?: FinisState }
        .stateIn(scope, Eagerly, EmptyState)

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
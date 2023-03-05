package dmitry.molchanov.presentation.details

import dmitry.molchanov.presentation.details.DetailsViewModel.DeleteItem
import dmitry.molchanov.presentation.details.DetailsViewModel.Intent
import dmitry.molchanov.presentation.details.DetailsViewModel.State
import dmitry.molchanov.presentation.details.DetailsViewModel.SwitchDoneFlag
import dmitry.molchanov.presentation.details.DetailsViewModel.TextChange
import dmitry.molchanov.usecase.EditTodoItemUseCase
import dmitry.molchanov.usecase.GetTodoItemsUseCase
import dmitry.molchanov.usecase.RemoveTodoItemUseCase
import dmitry.molchanov.util.Dispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
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

    override val state: StateFlow<State> = getTodoItemsUseCase.execute()
        .mapNotNull { todoItems -> todoItems.find { it.id == itemId } }
        .map(::State)
        .stateIn(scope, SharingStarted.Lazily, State())

    override fun onIntent(intent: Intent) {
        scope.launch {
            when (intent) {
                DeleteItem ->
                    removeTodoItemUseCase.execute(itemId)
                SwitchDoneFlag ->
                    editTodoUseCase.execute(
                        todoItem = state.value.todoItem.let { it.copy(isDone = !it.isDone) }
                    )
                is TextChange ->
                    editTodoUseCase.execute(
                        todoItem = state.value.todoItem.copy(text = intent.text)
                    )
                DetailsViewModel.Release ->
                    scope.cancel()
            }
        }
    }

}
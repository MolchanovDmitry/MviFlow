package dmitry.molchanov.presentation.main

import dmitry.molchanov.model.TodoItem
import dmitry.molchanov.mvi.MviViewModelImpl
import dmitry.molchanov.presentation.main.MainStore.*
import dmitry.molchanov.usecase.AddTodoItemUseCase
import dmitry.molchanov.usecase.EditTodoItemUseCase
import dmitry.molchanov.usecase.GetTodoItemsUseCase
import dmitry.molchanov.usecase.RemoveTodoItemUseCase
import dmitry.molchanov.util.Dispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModelImpl(
    dispatcher: Dispatchers,
    reducer: MainViewModelReducer,
    getTodoItemsUseCase: GetTodoItemsUseCase,
    private val addUseCase: AddTodoItemUseCase,
    private val editUseChange: EditTodoItemUseCase,
    private val removeUseCase: RemoveTodoItemUseCase,
) : MviViewModelImpl<State, Intent, Message, SideEffect>(
    initState = State(),
    reducer = reducer
) {

    private val scope = CoroutineScope(dispatcher.io + SupervisorJob())

    init {
        getTodoItemsUseCase.execute()
            .onEach { todoItems -> dispatch(UpdateTodoItemsMsg(todoItems)) }
            .launchIn(scope)
    }

    override fun onIntent(intent: Intent) {
        scope.launch {
            when (intent) {
                AddTodoItem ->
                    addUseCase.execute(state.value.text)
                        .onSuccess { dispatch(UpdateText(text = "")) }
                        .onFailure { publish(EmptyAddText) }

                is EditItem ->
                    getItemById(intent.itemId)
                        ?.let { todoItem -> editUseChange.execute(todoItem) }
                        ?: itemNotFound()

                is DeleteItem ->
                    removeUseCase.execute(intent.itemId)

                is TextChange ->
                    dispatch(UpdateText(text = intent.text))

                is ClickItem ->
                    getItemById(intent.itemId)
                        ?.let(::ItemClick)
                        ?.let { sideEffect -> publish(sideEffect) }
                        ?: itemNotFound()

                is SwitchDoneFlag ->
                    getItemById(intent.itemId)
                        ?.let { it.copy(isDone = !it.isDone) }
                        ?.let { todoItem -> editUseChange.execute(todoItem) }
                        ?: itemNotFound()
            }
        }
    }

    override fun clear() {
        scope.cancel()
    }

    private suspend fun itemNotFound() = publish(TodoItemNotFound)

    private fun getItemById(id: Long): TodoItem? = state.value.todoItems.find { it.id == id }
}
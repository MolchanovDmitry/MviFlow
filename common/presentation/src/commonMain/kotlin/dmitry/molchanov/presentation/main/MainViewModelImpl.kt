package dmitry.molchanov.presentation.main

import dmitry.molchanov.model.TodoItem
import dmitry.molchanov.presentation.main.MainViewModel.AddTodoItem
import dmitry.molchanov.presentation.main.MainViewModel.ClickItem
import dmitry.molchanov.presentation.main.MainViewModel.DeleteItem
import dmitry.molchanov.presentation.main.MainViewModel.EditItem
import dmitry.molchanov.presentation.main.MainViewModel.EmptyAddText
import dmitry.molchanov.presentation.main.MainViewModel.Intent
import dmitry.molchanov.presentation.main.MainViewModel.ItemClick
import dmitry.molchanov.presentation.main.MainViewModel.Release
import dmitry.molchanov.presentation.main.MainViewModel.SideEffect
import dmitry.molchanov.presentation.main.MainViewModel.State
import dmitry.molchanov.presentation.main.MainViewModel.SwitchDoneFlag
import dmitry.molchanov.presentation.main.MainViewModel.TextChange
import dmitry.molchanov.presentation.main.MainViewModel.TodoItemNotFound
import dmitry.molchanov.usecase.AddTodoItemUseCase
import dmitry.molchanov.usecase.EditTodoItemUseCase
import dmitry.molchanov.usecase.GetTodoItemsUseCase
import dmitry.molchanov.usecase.RemoveTodoItemUseCase
import dmitry.molchanov.util.Dispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModelImpl(
    dispatcher: Dispatchers,
    getTodoItemsUseCase: GetTodoItemsUseCase,
    private val addUseCase: AddTodoItemUseCase,
    private val editUseChange: EditTodoItemUseCase,
    private val removeUseCase: RemoveTodoItemUseCase,
) : MainViewModel {

    private val scope =
        CoroutineScope(dispatcher.io + SupervisorJob()) // на уровне вью модели все может выполняться в фоновом потоке

    private val sideEffectFlow = MutableSharedFlow<SideEffect>()
    override val sideEffect = sideEffectFlow.asSharedFlow()

    private val stateFlow = MutableStateFlow(State())
    override val state = stateFlow.asStateFlow()

    init {
        getTodoItemsUseCase.execute()
            .onEach { todoItems -> stateFlow.update { it.copy(todoItems = todoItems) } }
            .launchIn(scope)
    }

    override fun onIntent(intent: Intent) {
        scope.launch {
            when (intent) {
                AddTodoItem ->
                    addUseCase.execute(state.value.text)
                        .onSuccess { stateFlow.update { it.copy(text = "") } }
                        .onFailure { sideEffectFlow.emit(EmptyAddText) }

                is EditItem ->
                    getItemById(intent.itemId)
                        ?.let { todoItem -> editUseChange.execute(todoItem) }
                        ?: itemNotFound()

                is DeleteItem ->
                    removeUseCase.execute(intent.itemId)

                is TextChange ->
                    stateFlow.update { it.copy(text = intent.text) }

                is ClickItem ->
                    getItemById(intent.itemId)
                        ?.let(::ItemClick)
                        ?.let(sideEffectFlow::tryEmit)
                        ?: itemNotFound()

                is SwitchDoneFlag ->
                    getItemById(intent.itemId)
                        ?.let { it.copy(isDone = !it.isDone) }
                        ?.let { todoItem -> editUseChange.execute(todoItem) }
                        ?: itemNotFound()
                Release ->
                    scope.cancel()
            }
        }
    }

    private suspend fun itemNotFound() = sideEffectFlow.emit(TodoItemNotFound)

    private fun getItemById(id: Long): TodoItem? = state.value.todoItems.find { it.id == id }
}
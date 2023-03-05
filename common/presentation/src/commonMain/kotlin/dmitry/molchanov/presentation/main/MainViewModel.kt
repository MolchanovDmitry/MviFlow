package dmitry.molchanov.presentation.main

import dmitry.molchanov.model.TodoItem
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface MainViewModel {

    val state: StateFlow<State>

    val sideEffect: SharedFlow<SideEffect>

    fun onIntent(intent: Intent)

    data class State(
        val todoItems: List<TodoItem> = emptyList(),
        val text: String = ""
    )

    sealed class Intent
    object AddTodoItem : Intent()
    object Release: Intent()
    class ClickItem(val itemId: Long) : Intent()
    class EditItem(val itemId: Long) : Intent()
    class DeleteItem(val itemId: Long) : Intent()
    class SwitchDoneFlag(val itemId: Long) : Intent()
    class TextChange(val text: String) : Intent()

    sealed class SideEffect
    object EmptyAddText : SideEffect()
    object TodoItemNotFound : SideEffect()
    class ItemClick(val todoItem: TodoItem) : SideEffect()

}


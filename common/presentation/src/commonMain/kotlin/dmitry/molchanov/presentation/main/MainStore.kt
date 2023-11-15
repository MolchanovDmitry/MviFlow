package dmitry.molchanov.presentation.main

import dmitry.molchanov.model.TodoItem

interface MainStore {

    data class State(
        val todoItems: List<TodoItem> = emptyList(),
        val text: String = ""
    )

    sealed class Intent
    object AddTodoItem : Intent()
    class ClickItem(val itemId: Long) : Intent()
    class EditItem(val itemId: Long) : Intent()
    class DeleteItem(val itemId: Long) : Intent()
    class SwitchDoneFlag(val itemId: Long) : Intent()
    class TextChange(val text: String) : Intent()

    sealed class SideEffect
    object EmptyAddText : SideEffect()
    object TodoItemNotFound : SideEffect()
    class ItemClick(val todoItem: TodoItem) : SideEffect()

    sealed class Message
    class UpdateTodoItemsMsg(val todoItems: List<TodoItem>) : Message()
    class UpdateText(val text: String) : Message()

}


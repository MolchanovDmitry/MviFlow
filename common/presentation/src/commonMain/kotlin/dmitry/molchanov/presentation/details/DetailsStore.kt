package dmitry.molchanov.presentation.details

import dmitry.molchanov.model.TodoItem

interface DetailsStore{

    sealed class State
    object EmptyState : State()
    object FinisState : State()
    data class ItemState(val todoItem: TodoItem) : State()

    sealed class Intent
    object DeleteItem : Intent()
    object SwitchDoneFlag : Intent()
    class TextChange(val text: String) : Intent()

    sealed class Message
    object FinishMsg: Message()
    class SetCurrentItemMsg(val todoItem: TodoItem):Message()

}
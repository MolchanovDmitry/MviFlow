package dmitry.molchanov.presentation.details

import dmitry.molchanov.model.TodoItem
import kotlinx.coroutines.flow.StateFlow

interface DetailsViewModel {
    val state: StateFlow<State>

    fun onIntent(intent: Intent)

    data class State(val todoItem: TodoItem = TodoItem(id = 0, text = "", isDone = false))

    sealed class Intent
    object DeleteItem : Intent()
    object Release : Intent()
    object SwitchDoneFlag : Intent()
    class TextChange(val text: String) : Intent()

}
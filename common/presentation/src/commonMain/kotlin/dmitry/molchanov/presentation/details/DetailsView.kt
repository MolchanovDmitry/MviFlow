package dmitry.molchanov.presentation.details

import dmitry.molchanov.model.TodoItem
import dmitry.molchanov.mvi.MviView
import dmitry.molchanov.presentation.details.DetailsView.Event

abstract class DetailsView : MviView<TodoItem, Event>() {

    sealed class Event {
        data class TextChanged(val text: String) : Event()
        object DoneClicked : Event()
        object DeleteClicked : Event()
    }
}
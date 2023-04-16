package dmitry.molchanov.presentation.details

import dmitry.molchanov.mvi.MviView
import dmitry.molchanov.presentation.details.DetailsView.Event
import dmitry.molchanov.presentation.details.DetailsView.Model

abstract class DetailsView : MviView<Model, Event>() {

    data class Model(val text: String, val isDone: Boolean)

    sealed class Event {
        data class TextChanged(val text: String) : Event()
        object DoneClicked : Event()
        object DeleteClicked : Event()
    }
}
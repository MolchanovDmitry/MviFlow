package dmitry.molchanov.mvi_kotlin_app.domain.details

import com.arkivanov.mvikotlin.core.view.MviView
import dmitry.molchanov.mvi_kotlin_app.domain.details.DetailsView.Event
import dmitry.molchanov.mvi_kotlin_app.domain.details.DetailsView.Model

interface DetailsView : MviView<Model, Event> {

    data class Model(
        val text: String,
        val isDone: Boolean,
    ) {
        // No-arg constructor for Swift.
        constructor() : this(
            text = "",
            isDone = false,
        )
    }

    sealed class Event {
        data class TextChanged(val text: String) : Event()
        object DoneClicked : Event()
        object DeleteClicked : Event()
    }
}

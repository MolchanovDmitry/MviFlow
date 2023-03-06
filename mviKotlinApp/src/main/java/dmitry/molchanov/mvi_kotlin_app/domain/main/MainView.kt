package dmitry.molchanov.mvi_kotlin_app.domain.main

import com.arkivanov.mvikotlin.core.view.MviView
import dmitry.molchanov.mvi_kotlin_app.domain.main.MainView.Event
import dmitry.molchanov.mvi_kotlin_app.domain.main.MainView.Model

interface MainView : MviView<Model, Event> {

    data class Model(
        val items: List<Item>,
        val text: String,
    ) {
        // No-arg constructor for Swift.
        constructor() : this(
            items = emptyList(),
            text = "",
        )

        data class Item(
            val id: Long,
            val text: String,
            val isDone: Boolean,
        )
    }

    sealed class Event {
        data class ItemClicked(val id: Long) : Event()
        data class ItemDoneClicked(val id: Long) : Event()
        data class ItemDeleteClicked(val id: Long) : Event()
        data class TextChanged(val text: String) : Event()
        object AddClicked : Event()
    }
}

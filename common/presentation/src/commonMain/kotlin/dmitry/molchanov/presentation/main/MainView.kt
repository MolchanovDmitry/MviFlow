package dmitry.molchanov.presentation.main

import dmitry.molchanov.mvi.MviView
import dmitry.molchanov.presentation.main.MainView.Event
import dmitry.molchanov.presentation.main.MainView.Model

interface MainView<T, U> : MviView<Model, Event> {

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

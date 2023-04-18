package dmitry.molchanov.presentation.main

import dmitry.molchanov.mvi.MviSideEffectHandler
import dmitry.molchanov.mvi.MviView
import dmitry.molchanov.presentation.main.MainView.Effect
import dmitry.molchanov.presentation.main.MainView.Event
import dmitry.molchanov.presentation.main.MainView.Model

abstract class MainView : MviView<Model, Event>(), MviSideEffectHandler<Effect> {

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

    sealed class Effect
    object ShowEmptyMessage : Effect()
    object ShowNotFoundMessage : Effect()

}

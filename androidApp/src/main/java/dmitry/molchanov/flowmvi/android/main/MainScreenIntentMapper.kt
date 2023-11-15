package dmitry.molchanov.flowmvi.android.main

import dmitry.molchanov.mvi.MviIntentMapper
import dmitry.molchanov.presentation.main.MainStore
import dmitry.molchanov.presentation.main.MainView
import dmitry.molchanov.presentation.main.MainView.Event

class MainScreenIntentMapper : MviIntentMapper<MainView.Event, MainStore.Intent> {

    override val map: (Event) -> MainStore.Intent = { event ->
        when (event) {
            Event.AddClicked -> MainStore.AddTodoItem
            is Event.ItemClicked -> MainStore.ClickItem(event.id)
            is Event.ItemDeleteClicked -> MainStore.DeleteItem(event.id)
            is Event.ItemDoneClicked -> MainStore.SwitchDoneFlag(event.id)
            is Event.TextChanged -> MainStore.TextChange(event.text)
        }
    }
}
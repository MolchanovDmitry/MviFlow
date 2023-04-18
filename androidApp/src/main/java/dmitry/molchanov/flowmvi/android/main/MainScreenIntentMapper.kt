package dmitry.molchanov.flowmvi.android.main

import dmitry.molchanov.mvi.MviIntentMapper
import dmitry.molchanov.presentation.main.MainView
import dmitry.molchanov.presentation.main.MainView.Event
import dmitry.molchanov.presentation.main.MainViewModel

class MainScreenIntentMapper : MviIntentMapper<MainView.Event, MainViewModel.Intent> {

    override val map: (Event) -> MainViewModel.Intent = { event ->
        when (event) {
            Event.AddClicked -> MainViewModel.AddTodoItem
            is Event.ItemClicked -> MainViewModel.ClickItem(event.id)
            is Event.ItemDeleteClicked -> MainViewModel.DeleteItem(event.id)
            is Event.ItemDoneClicked -> MainViewModel.SwitchDoneFlag(event.id)
            is Event.TextChanged -> MainViewModel.TextChange(event.text)
        }
    }
}
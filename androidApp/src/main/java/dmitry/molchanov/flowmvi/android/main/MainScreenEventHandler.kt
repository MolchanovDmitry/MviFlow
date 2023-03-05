package dmitry.molchanov.flowmvi.android.main

import dmitry.molchanov.presentation.main.MainView.Event
import dmitry.molchanov.presentation.main.MainViewModel

class MainScreenEventHandler(private val vm: MainViewModel) {
    fun handle(event: Event) {
        when (event) {
            Event.AddClicked -> MainViewModel.AddTodoItem
            is Event.ItemClicked -> MainViewModel.ClickItem(event.id)
            is Event.ItemDeleteClicked -> MainViewModel.DeleteItem(event.id)
            is Event.ItemDoneClicked -> MainViewModel.SwitchDoneFlag(event.id)
            is Event.TextChanged -> MainViewModel.TextChange(event.text)
        }.let(vm::onIntent)
    }
}
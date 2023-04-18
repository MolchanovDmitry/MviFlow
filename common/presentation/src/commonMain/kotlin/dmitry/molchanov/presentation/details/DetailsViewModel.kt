package dmitry.molchanov.presentation.details

import dmitry.molchanov.model.TodoItem
import dmitry.molchanov.mvi.MviViewModel
import dmitry.molchanov.presentation.details.DetailsViewModel.Intent
import dmitry.molchanov.presentation.details.DetailsViewModel.State

interface DetailsViewModel: MviViewModel<State, Intent> {

    sealed class State
    object EmptyState : State()
    object FinisState : State()
    data class ItemState(val todoItem: TodoItem) : State()

    sealed class Intent
    object DeleteItem : Intent()
    object SwitchDoneFlag : Intent()
    class TextChange(val text: String) : Intent()

}
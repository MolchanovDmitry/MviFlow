package dmitry.molchanov.presentation.main

import dmitry.molchanov.mvi.Reducer
import dmitry.molchanov.presentation.main.MainStore.*

class MainViewModelReducer : Reducer<Message, State> {
    override fun reduce(currentState: State, msg: Message): State =
        when (msg) {
            is UpdateText -> currentState.copy(text = msg.text)
            is UpdateTodoItemsMsg -> currentState.copy(todoItems = msg.todoItems)
        }
}
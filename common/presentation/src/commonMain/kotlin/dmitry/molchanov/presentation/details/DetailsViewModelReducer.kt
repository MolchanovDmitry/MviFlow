package dmitry.molchanov.presentation.details

import dmitry.molchanov.mvi.Reducer
import dmitry.molchanov.presentation.details.DetailsStore.*

class DetailsViewModelReducer : Reducer<Message, State> {
    override fun reduce(currentState: State, msg: Message): State  =
        when(msg){
            FinishMsg -> FinisState
            is SetCurrentItemMsg -> ItemState(msg.todoItem)
        }
}
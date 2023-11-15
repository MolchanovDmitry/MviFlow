package dmitry.molchanov.flowmvi.android.details

import dmitry.molchanov.mvi.MviIntentMapper
import dmitry.molchanov.presentation.details.DetailsView
import dmitry.molchanov.presentation.details.DetailsStore

class DetailsViewEventHandler : MviIntentMapper<DetailsView.Event, DetailsStore.Intent> {

    override val map: (DetailsView.Event) -> DetailsStore.Intent = { event ->
        when (event) {
            DetailsView.Event.DeleteClicked -> DetailsStore.DeleteItem
            DetailsView.Event.DoneClicked -> DetailsStore.SwitchDoneFlag
            is DetailsView.Event.TextChanged -> DetailsStore.TextChange(event.text)
        }
    }
}
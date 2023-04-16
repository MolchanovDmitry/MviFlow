package dmitry.molchanov.flowmvi.android.details

import dmitry.molchanov.mvi.MviIntentMapper
import dmitry.molchanov.presentation.details.DetailsView
import dmitry.molchanov.presentation.details.DetailsViewModel

class DetailsViewEventHandler : MviIntentMapper<DetailsView.Event, DetailsViewModel.Intent> {

    override val map: (DetailsView.Event) -> DetailsViewModel.Intent = { event ->
        when (event) {
            DetailsView.Event.DeleteClicked -> DetailsViewModel.DeleteItem
            DetailsView.Event.DoneClicked -> DetailsViewModel.SwitchDoneFlag
            is DetailsView.Event.TextChanged -> DetailsViewModel.TextChange(event.text)
        }
    }
}
package dmitry.molchanov.flowmvi.android.details

import dmitry.molchanov.presentation.details.DetailsView
import dmitry.molchanov.presentation.details.DetailsViewModel

class DetailsViewEventHandler(private val vm: DetailsViewModel) {

    fun handle(event: DetailsView.Event){
        when(event){
            DetailsView.Event.DeleteClicked -> DetailsViewModel.DeleteItem
            DetailsView.Event.DoneClicked -> DetailsViewModel.SwitchDoneFlag
            is DetailsView.Event.TextChanged -> DetailsViewModel.TextChange(event.text)
        }.let(vm::onIntent)
    }
}
package dmitry.molchanov.presentation

import androidx.lifecycle.ViewModel
import dmitry.molchanov.mvi.MviViewModel
import dmitry.molchanov.presentation.details.DetailsStore.*
import dmitry.molchanov.presentation.details.DetailsViewModelImpl

class DetailsVM(
    private val detailsViewModel: DetailsViewModelImpl
) : ViewModel(),
    MviViewModel<State, Intent, Message, Nothing> by detailsViewModel {

    override fun onCleared() {
        super.onCleared()
        detailsViewModel.clear()
    }

}
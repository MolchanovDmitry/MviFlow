package dmitry.molchanov.presentation

import androidx.lifecycle.ViewModel
import dmitry.molchanov.presentation.details.DetailsViewModel

class DetailsVM(
    private val detailsViewModel: DetailsViewModel
) : ViewModel(),
    DetailsViewModel by detailsViewModel {

    override fun onCleared() {
        super.onCleared()
        detailsViewModel.clear()
    }

}
package dmitry.molchanov.presentation

import androidx.lifecycle.ViewModel
import dmitry.molchanov.presentation.main.MainViewModel

class MainViewModelPlatformImpl(
    private val mainViewModel: MainViewModel
) : ViewModel(),
    MainViewModel by mainViewModel {

    override fun onCleared() {
        super.onCleared()
        onIntent(MainViewModel.Release)
    }
}
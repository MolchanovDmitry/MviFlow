package dmitry.molchanov.presentation

import androidx.lifecycle.ViewModel
import dmitry.molchanov.presentation.main.MainViewModel

class MainVM(
    private val mainViewModel: MainViewModel
) : ViewModel(),
    MainViewModel by mainViewModel {

    override fun onCleared() {
        super.onCleared()
        println("112233 mainVM clear")
        mainViewModel.clear()
    }
}
package dmitry.molchanov.presentation

import androidx.lifecycle.ViewModel
import dmitry.molchanov.mvi.MviViewModel
import dmitry.molchanov.presentation.main.MainStore.Intent
import dmitry.molchanov.presentation.main.MainStore.Message
import dmitry.molchanov.presentation.main.MainStore.SideEffect
import dmitry.molchanov.presentation.main.MainStore.State
import dmitry.molchanov.presentation.main.MainViewModelImpl

class MainVM(
    private val mainViewModelImpl: MainViewModelImpl
) : ViewModel(),
    MviViewModel<State, Intent, Message, SideEffect> by mainViewModelImpl {

    override fun onCleared() {
        super.onCleared()
        mainViewModelImpl.clear()
    }
}
package dmitry.molchanov.mvi

import kotlinx.coroutines.flow.StateFlow

interface MviViewModel<State> {

    val state: StateFlow<State>

    fun clear()

}
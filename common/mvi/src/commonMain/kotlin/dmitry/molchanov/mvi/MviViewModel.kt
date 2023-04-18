package dmitry.molchanov.mvi

import kotlinx.coroutines.flow.StateFlow

interface MviViewModel<State, Intent> {

    val state: StateFlow<State>

    fun onIntent(intent: Intent)

    fun clear()

}
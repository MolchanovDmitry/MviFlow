package dmitry.molchanov.mvi

import kotlinx.coroutines.flow.Flow

interface MviViewModel<State> {

    val state: Flow<State>

    fun clear()

}
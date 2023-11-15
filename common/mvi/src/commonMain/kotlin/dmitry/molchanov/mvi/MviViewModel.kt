package dmitry.molchanov.mvi

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface MviViewModel<State, Intent, Message, Label> {

    val sideEffect: SharedFlow<Label>

    val state: StateFlow<State>

    fun onIntent(intent: Intent)

    fun clear()

    fun dispatch(msg: Message)

    suspend fun publish(label: Label)

    suspend fun tryPublish(label: Label)
}

abstract class MviViewModelImpl<State, Intent, Message, Label>(
    initState: State,
    private val reducer: Reducer<Message, State>
) : MviViewModel<State, Intent, Message, Label> {

    private val _sideEffectFlow by lazy { MutableSharedFlow<Label>(extraBufferCapacity = 1) }
    override val sideEffect by lazy { _sideEffectFlow.asSharedFlow() }

    private val _state = MutableStateFlow<State>(initState)
    override val state: StateFlow<State> = _state.asStateFlow()

    abstract override fun onIntent(intent: Intent)

    abstract override fun clear()

    override fun dispatch(msg: Message) {
        val newState = reducer.reduce(state.value, msg)
        _state.update { newState }
    }

    override suspend fun publish(label: Label) {
        _sideEffectFlow.emit(label)
    }

    override suspend fun tryPublish(label: Label) {
        _sideEffectFlow.tryEmit(label)
    }
}
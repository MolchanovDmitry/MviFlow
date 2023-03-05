package dmitry.molchanov.mvi_kotlin_app.domain.main.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.JvmSerializable
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dmitry.molchanov.mvi_kotlin_app.domain.main.store.AddStore.Intent
import dmitry.molchanov.mvi_kotlin_app.domain.main.store.AddStore.Label
import dmitry.molchanov.mvi_kotlin_app.domain.main.store.AddStore.State
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class AddStoreFactory(
    private val storeFactory: StoreFactory,
    private val mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
) {

    fun create(): AddStore =
        object : AddStore, Store<Intent, State, Label> by storeFactory.create(
            name = "TodoAddStore",
            initialState = State(),
            executorFactory =(::ExecutorImpl),
            reducer = ReducerImpl,
        ) {}

    // Serializable only for exporting events in Time Travel, no need otherwise.
    private sealed class Msg : JvmSerializable {
        data class TextChanged(val text: String) : Msg()
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Nothing, State, Msg, Label>(mainContext) {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.SetText -> dispatch(Msg.TextChanged(intent.text))
                is Intent.Add -> addItem(getState())
            }.let {}
        }

        private fun addItem(state: State) {
            val text = state.text.takeUnless(String::isBlank) ?: return

            dispatch(Msg.TextChanged(""))

            scope.launch {
                // val item = withContext(ioContext) { database.create(TodoItem.Data(text = text)) } TODO
                // publish(Label.Added(item))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.TextChanged -> copy(text = msg.text)
            }
    }
}

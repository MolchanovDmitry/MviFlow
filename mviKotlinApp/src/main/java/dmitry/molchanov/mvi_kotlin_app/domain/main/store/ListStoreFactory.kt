package dmitry.molchanov.mvi_kotlin_app.domain.main.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.JvmSerializable
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dmitry.molchanov.model.TodoItem
import dmitry.molchanov.model.TodoItemDataStore
import dmitry.molchanov.mvi_kotlin_app.domain.main.store.ListStore.Intent
import dmitry.molchanov.mvi_kotlin_app.domain.main.store.ListStore.State
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class ListStoreFactory(
    private val storeFactory: StoreFactory,
    private val mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
    private val dataStore: TodoItemDataStore
) {

    fun create(): ListStore =
        object : ListStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "ListStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl,
        ) {}

    // Serializable only for exporting events in Time Travel, no need otherwise.
    private sealed interface Msg : JvmSerializable {
        data class Loaded(val items: List<TodoItem>) : Msg
        data class Deleted(val id: Long) : Msg
        data class DoneToggled(val id: Long) : Msg
        data class Added(val item: TodoItem) : Msg
        data class Changed(val item: TodoItem) : Msg
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Unit, State, Msg, Nothing>(mainContext) {
        override fun executeAction(action: Unit, getState: () -> State) {
            dataStore.todoItemsFlow
                .map(Msg::Loaded)
                .onEach(::dispatch)
                .launchIn(scope)
        }

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.Delete -> delete(intent.id)
                is Intent.ToggleDone -> toggleDone(intent.id, getState)
                is Intent.AddToState -> dispatch(Msg.Added(intent.item))
                is Intent.DeleteFromState -> dispatch(Msg.Deleted(intent.id))
                is Intent.UpdateInState -> dispatch(Msg.Changed(intent.todoItem))
            }.let {}
        }

        private fun delete(id: Long) {
            dispatch(Msg.Deleted(id))

            scope.launch(ioContext) {
                dataStore.removeItem(id)
            }
        }

        private fun toggleDone(id: Long, state: () -> State) {
            //dispatch(Msg.DoneToggled(id))

            val item = state().items.find { it.id == id } ?: return

            scope.launch(ioContext) {
                dataStore.updateItem(item)
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.Loaded -> copy(items = msg.items)
                is Msg.Deleted -> copy(items = items.filterNot { it.id == msg.id })
                is Msg.DoneToggled -> copy(items = items)
                is Msg.Added -> copy(items = items + msg.item) // TODO
                is Msg.Changed -> copy(items = items) // TODO
            }
    }
}

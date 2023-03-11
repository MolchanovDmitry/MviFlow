package dmitry.molchanov.mvi_kotlin_app.domain.details.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.core.utils.JvmSerializable
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutorScope
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import dmitry.molchanov.model.TodoItem
import dmitry.molchanov.model.TodoItemDataStore
import dmitry.molchanov.mvi_kotlin_app.domain.details.store.DetailsStore.Intent
import dmitry.molchanov.mvi_kotlin_app.domain.details.store.DetailsStore.Label
import dmitry.molchanov.mvi_kotlin_app.domain.details.store.DetailsStore.State
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalMviKotlinApi::class)
internal class DetailsStoreFactory(
    private val storeFactory: StoreFactory,
    private val mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
    private val dataStore: TodoItemDataStore,
    private val itemId: Long,
) {

    fun create(): DetailsStore =
        object : DetailsStore,
            Store<Intent, State, Label> by storeFactory.create<Intent, Unit, Msg, State, Label>(
                name = "TodoDetailsStore",
                initialState = State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = coroutineExecutorFactory(mainContext) {
                    onAction<Unit> {
                        launch {
                            withContext(ioContext) {
                                dataStore.todoItemsFlow.firstOrNull { items ->
                                    val item = items.find { todoItem ->
                                        todoItem.id == itemId
                                    }
                                    val event = item?.let(Msg::Loaded) ?: Msg.Finished
                                    withContext(mainContext) {
                                        dispatch(event)
                                    }
                                    true
                                }
                            }
                        }
                    }

                    onIntent<Intent.SetText> {
                        dispatch(Msg.TextChanged(it.text))
                        save()
                    }

                    onIntent<Intent.ToggleDone> {
                        dispatch(Msg.DoneToggled)
                        save()
                    }

                    onIntent<Intent.Delete> {
                        publish(Label.Deleted(itemId))

                        launch {
                            withContext(ioContext) {
                                dataStore.removeItem(itemId)
                            }
                            dispatch(Msg.Finished)
                        }
                    }
                },
                reducer = { msg ->
                    when (msg) {
                        is Msg.Loaded -> copy(todoItem = msg.todoItem)
                        is Msg.Finished -> copy(isFinished = true)
                        is Msg.TextChanged -> copy(todoItem = todoItem?.copy(text = msg.text))
                        is Msg.DoneToggled -> copy(todoItem = todoItem?.copy(isDone = !todoItem.isDone))
                    }
                },
            ) {}


    // Serializable only for exporting events in Time Travel, no need otherwise.
    private sealed class Msg : JvmSerializable {
        data class Loaded(val todoItem: TodoItem) : Msg()
        object Finished : Msg()
        data class TextChanged(val text: String) : Msg()
        object DoneToggled : Msg()
    }

    private fun CoroutineExecutorScope<State, Msg, Label>.save() {
        val item = state.todoItem ?: return
        item.let(Label::Changed)
            .let(::publish)

        launch(ioContext) {
            dataStore.updateItem(item)
        }
    }
}

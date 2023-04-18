package dmitry.molchanov.mvi_kotlin_app.domain.main

import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.events
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import dmitry.molchanov.model.TodoItem
import dmitry.molchanov.mvi_kotlin_app.domain.main.MainView.Event
import dmitry.molchanov.mvi_kotlin_app.domain.main.store.AddStore
import dmitry.molchanov.mvi_kotlin_app.domain.main.store.ListStore
import dmitry.molchanov.util.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapNotNull

class MainController(
    lifecycle: Lifecycle,
    private val dispatchers: Dispatchers,
    private val listStore: ListStore,
    private val addStore: AddStore,
    private val onItemSelected: (id: Long) -> Unit,
) {

    init {
        bind(lifecycle, BinderLifecycleMode.CREATE_DESTROY, dispatchers.unconfined) {
            addStore.labels.mapNotNull(addLabelToListIntent) bindTo listStore
        }
    }

    fun onViewCreated(view: MainView, viewLifecycle: Lifecycle) {
        bind(viewLifecycle, BinderLifecycleMode.CREATE_DESTROY, dispatchers.unconfined) {
            view.events.mapNotNull(eventToListIntent) bindTo listStore
            view.events.mapNotNull(eventToAddIntent) bindTo addStore
        }

        bind(viewLifecycle, BinderLifecycleMode.START_STOP, dispatchers.unconfined) {
            combine(listStore.states, addStore.states, statesToModel) bindTo view
            view.events bindTo ::onEvent
        }
    }

    private fun onEvent(event: Event) {
        when (event) {
            is Event.ItemClicked -> onItemSelected(event.id)
            is Event.AddClicked,
            is Event.ItemDeleteClicked,
            is Event.ItemDoneClicked,
            is Event.TextChanged -> null
        }.let {}
    }

    fun onItemChanged(id: Long, text: String, isDone: Boolean) {
        listStore.accept(
            ListStore.Intent.UpdateInState(
                todoItem = TodoItem(
                    id = id,
                    text = text,
                    isDone = isDone
                )
            )
        )
    }

    fun onItemDeleted(id: Long) {
        listStore.accept(ListStore.Intent.DeleteFromState(id = id))
    }
}

package dmitry.molchanov.mvi_kotlin_app.domain.details

import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.events
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import dmitry.molchanov.model.TodoItem
import dmitry.molchanov.mvi_kotlin_app.domain.details.store.DetailsStore
import dmitry.molchanov.mvi_kotlin_app.domain.details.store.DetailsStore.Label
import dmitry.molchanov.util.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class DetailsController(
    lifecycle: Lifecycle,
    private val dispatchers: Dispatchers,
    private val detailsStore: DetailsStore,
    private val onItemChanged: (todoItem: TodoItem) -> Unit,
    private val onItemDeleted: (id: Long) -> Unit,
) {

    init {
        lifecycle.doOnDestroy(detailsStore::dispose)

        bind(lifecycle, BinderLifecycleMode.CREATE_DESTROY, dispatchers.unconfined) {
            detailsStore.labels bindTo { label ->
                when (label) {
                    is Label.Changed -> onItemChanged(label.todoItem)
                    is Label.Deleted -> onItemDeleted(label.id)
                }.let {}
            }
        }
    }

    fun onViewCreated(view: DetailsView, viewLifecycle: Lifecycle) {
        bind(viewLifecycle, BinderLifecycleMode.START_STOP, dispatchers.unconfined) {
            view.events.mapNotNull(eventToIntent) bindTo detailsStore
            detailsStore.states.map(stateToModel) bindTo view
        }
    }
}

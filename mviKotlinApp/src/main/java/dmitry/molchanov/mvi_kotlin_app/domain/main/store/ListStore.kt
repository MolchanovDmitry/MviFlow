package dmitry.molchanov.mvi_kotlin_app.domain.main.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.utils.JvmSerializable
import dmitry.molchanov.model.TodoItem
import dmitry.molchanov.mvi_kotlin_app.domain.main.store.ListStore.Intent
import dmitry.molchanov.mvi_kotlin_app.domain.main.store.ListStore.State

interface ListStore : Store<Intent, State, Nothing> {

    // Serializable only for exporting events in Time Travel, no need otherwise.
    sealed class Intent : JvmSerializable {
        data class Delete(val id: Long) : Intent()
        data class ToggleDone(val id: Long) : Intent()
        data class AddToState(val item: TodoItem) : Intent()
        data class DeleteFromState(val id: Long) : Intent()
        data class UpdateInState(val todoItem: TodoItem) : Intent()
    }

    data class State(
        val items: List<TodoItem> = emptyList(),
    ) : JvmSerializable // Serializable only for exporting events in Time Travel, no need otherwise.
}

package dmitry.molchanov.mvi_kotlin_app.domain.details.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.utils.JvmSerializable
import dmitry.molchanov.model.TodoItem
import dmitry.molchanov.mvi_kotlin_app.domain.details.store.DetailsStore.Intent
import dmitry.molchanov.mvi_kotlin_app.domain.details.store.DetailsStore.Label
import dmitry.molchanov.mvi_kotlin_app.domain.details.store.DetailsStore.State

interface DetailsStore : Store<Intent, State, Label> {

    // Serializable only for exporting events in Time Travel, no need otherwise.
    sealed class Intent : JvmSerializable {
        data class SetText(val text: String) : Intent()
        object ToggleDone : Intent()
        object Delete : Intent()
    }

    data class State(
        val todoItem: TodoItem? = null,
        val isFinished: Boolean = false,
    ) : JvmSerializable // Serializable only for exporting events in Time Travel, no need otherwise.

    // Serializable only for exporting events in Time Travel, no need otherwise.
    sealed class Label : JvmSerializable {
        data class Changed(val todoItem: TodoItem) : Label()
        data class Deleted(val id: Long) : Label()
    }
}

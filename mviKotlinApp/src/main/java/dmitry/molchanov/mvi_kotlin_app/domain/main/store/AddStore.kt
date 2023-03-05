package dmitry.molchanov.mvi_kotlin_app.domain.main.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.utils.JvmSerializable
import dmitry.molchanov.mvi_kotlin_app.domain.TodoItem
import dmitry.molchanov.mvi_kotlin_app.domain.main.store.AddStore.Intent
import dmitry.molchanov.mvi_kotlin_app.domain.main.store.AddStore.Label
import dmitry.molchanov.mvi_kotlin_app.domain.main.store.AddStore.State

internal interface AddStore : Store<Intent, State, Label> {

    // Serializable only for exporting events in Time Travel, no need otherwise.
    sealed class Intent : JvmSerializable {
        data class SetText(val text: String) : Intent()
        object Add : Intent()
    }

    data class State(
        val text: String = "",
    ) : JvmSerializable // Serializable only for exporting events in Time Travel, no need otherwise.

    // Serializable only for exporting events in Time Travel, no need otherwise.
    sealed class Label : JvmSerializable {
        data class Added(val item: TodoItem) : Label()
    }
}

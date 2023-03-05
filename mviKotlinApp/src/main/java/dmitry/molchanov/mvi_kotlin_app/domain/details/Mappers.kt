package dmitry.molchanov.mvi_kotlin_app.domain.details

import dmitry.molchanov.mvi_kotlin_app.domain.details.DetailsView.Event
import dmitry.molchanov.mvi_kotlin_app.domain.details.DetailsView.Model
import dmitry.molchanov.mvi_kotlin_app.domain.details.store.DetailsStore.Intent
import dmitry.molchanov.mvi_kotlin_app.domain.details.store.DetailsStore.State

internal val stateToModel: (State) -> Model =
    { state ->
        Model(
            text = state.data?.text ?: "",
            isDone = state.data?.isDone ?: false,
        )
    }

internal val eventToIntent: (Event) -> Intent? =
    { event ->
        when (event) {
            is Event.TextChanged -> Intent.SetText(text = event.text)
            is Event.DoneClicked -> Intent.ToggleDone
            is Event.DeleteClicked -> Intent.Delete
        }
    }

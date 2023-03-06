package dmitry.molchanov.mvi_kotlin_app.domain.main

import dmitry.molchanov.mvi_kotlin_app.domain.main.MainView.Event
import dmitry.molchanov.mvi_kotlin_app.domain.main.MainView.Model
import dmitry.molchanov.mvi_kotlin_app.domain.main.store.AddStore
import dmitry.molchanov.mvi_kotlin_app.domain.main.store.ListStore

internal val statesToModel: (ListStore.State, AddStore.State) -> Model =
    { listState, addState ->
        Model(
            items = listState.items.map {
                Model.Item(id = it.id, text = it.text, isDone = it.isDone)
            },
            text = addState.text,
        )
    }

internal val addLabelToListIntent: (AddStore.Label) -> ListStore.Intent? =
    { label ->
        when (label) {
            is AddStore.Label.Added -> ListStore.Intent.AddToState(item = label.item)
        }
    }

internal val eventToListIntent: (Event) -> ListStore.Intent? =
    { event ->
        when (event) {
            is Event.ItemDeleteClicked -> ListStore.Intent.Delete(id = event.id)
            is Event.ItemDoneClicked -> ListStore.Intent.ToggleDone(id = event.id)
            is Event.ItemClicked,
            is Event.AddClicked,
            is Event.TextChanged -> null
        }
    }


internal val eventToAddIntent: (Event) -> AddStore.Intent? =
    { event ->
        when (event) {
            is Event.TextChanged -> AddStore.Intent.SetText(text = event.text)
            is Event.AddClicked -> AddStore.Intent.Add
            is Event.ItemClicked,
            is Event.ItemDeleteClicked,
            is Event.ItemDoneClicked -> null
        }
    }

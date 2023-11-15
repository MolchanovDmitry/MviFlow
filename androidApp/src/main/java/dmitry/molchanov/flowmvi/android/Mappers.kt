package dmitry.molchanov.flowmvi.android

import dmitry.molchanov.model.TodoItem
import dmitry.molchanov.presentation.main.MainStore
import dmitry.molchanov.presentation.main.MainView

val statesToModel: (MainStore.State) -> MainView.Model = { mainState ->
    MainView.Model(
        items = mainState.todoItems.map(TodoItem::mapToModelItem),
        text = mainState.text
    )
}

val sideEffectMapper: (MainStore.SideEffect) -> MainView.Effect? = { vmSideEffect ->
    when (vmSideEffect) {
        MainStore.EmptyAddText -> MainView.ShowEmptyMessage
        MainStore.TodoItemNotFound -> MainView.ShowNotFoundMessage
        is MainStore.ItemClick -> null
    }
}

private fun TodoItem.mapToModelItem(): MainView.Model.Item =
    MainView.Model.Item(id = id, text = text, isDone = isDone)


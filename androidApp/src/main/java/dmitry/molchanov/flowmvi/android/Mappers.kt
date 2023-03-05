package dmitry.molchanov.flowmvi.android

import dmitry.molchanov.model.TodoItem
import dmitry.molchanov.presentation.main.MainView
import dmitry.molchanov.presentation.main.MainViewModel

val statesToModel: (MainViewModel.State) -> MainView.Model = { mainState ->
    MainView.Model(
        items = mainState.todoItems.map(TodoItem::mapToModelItem),
        text = mainState.text
    )
}

private fun TodoItem.mapToModelItem(): MainView.Model.Item =
    MainView.Model.Item(id = id, text = text, isDone = isDone)


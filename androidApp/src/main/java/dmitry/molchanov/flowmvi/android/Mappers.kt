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

val sideEffectMapper: (MainViewModel.SideEffect) -> MainView.Effect? = { vmSideEffect ->
    when (vmSideEffect) {
        MainViewModel.EmptyAddText -> MainView.ShowEmptyMessage
        is MainViewModel.ItemClick -> MainView.ShowNotFoundMessage
        MainViewModel.TodoItemNotFound -> null
    }
}

val viewEventToIntent: (MainView.Event) -> MainViewModel.Intent = { event ->
    when(event){
        MainView.Event.AddClicked -> MainViewModel.AddTodoItem
        is MainView.Event.ItemClicked -> MainViewModel.ClickItem(event.id)
        is MainView.Event.ItemDeleteClicked -> MainViewModel.DeleteItem(event.id)
        is MainView.Event.ItemDoneClicked -> MainViewModel.SwitchDoneFlag(event.id)
        is MainView.Event.TextChanged -> MainViewModel.TextChange(event.text)
    }

}

private fun TodoItem.mapToModelItem(): MainView.Model.Item =
    MainView.Model.Item(id = id, text = text, isDone = isDone)


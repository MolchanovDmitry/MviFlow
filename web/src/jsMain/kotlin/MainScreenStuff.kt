import dmitry.molchanov.presentation.main.MainStore

sealed class Event {
    data class ItemClicked(val id: Long) : Event()
    data class ItemDoneClicked(val id: Long) : Event()
    data class ItemDeleteClicked(val id: Long) : Event()
    data class TextChanged(val text: String) : Event()
    object AddClicked : Event()
}

class MainScreenEventHandler(private val vm: MainStore) {

    fun handleAdd() = handle(Event.AddClicked)
    fun handleDone(id: Long)  = handle(Event.ItemDoneClicked(id))
    fun handleDelete(id: Long)  = handle(Event.ItemDeleteClicked(id))
    fun handleTextChange(text: String) = handle(Event.TextChanged(text))

    private fun handle(event: Event) {
        when (event) {
            Event.AddClicked -> MainStore.AddTodoItem
            is Event.ItemClicked -> MainStore.ClickItem(event.id)
            is Event.ItemDeleteClicked -> MainStore.DeleteItem(event.id)
            is Event.ItemDoneClicked -> MainStore.SwitchDoneFlag(event.id)
            is Event.TextChanged -> MainStore.TextChange(event.text)
        }.let(vm::onIntent)
    }
}
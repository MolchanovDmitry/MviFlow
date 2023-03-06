package dmitry.molchanov.model

data class TodoItem(
    val id: Long,
    val text: String,
    val isDone: Boolean = false
)
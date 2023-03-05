package dmitry.molchanov.model

sealed class TodoErrors(
    message: String? = null,
    cause: Throwable? = null
) : RuntimeException(message, cause)

object EmptyAddText: TodoErrors()
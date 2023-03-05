package dmitry.molchanov.mvi

interface MviView<in Model : Any, Event : Any> {
    val dispatch: (Event) -> Unit
    fun render(model: Model)
}
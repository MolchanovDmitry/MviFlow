package dmitry.molchanov.mvi

interface MviView<Model, Event> {

    val dispatch: (Event) -> Unit

    fun render(model: Model)
}
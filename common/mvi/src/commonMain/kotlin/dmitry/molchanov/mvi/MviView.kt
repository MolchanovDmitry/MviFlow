package dmitry.molchanov.mvi

abstract class MviView<Model, Event> {

    internal var dispatcher: ((Event) -> Unit)? = null

    open fun render(model: Model) = Unit

    fun dispatch(event: Event) {
        dispatcher?.invoke(event)
    }

}
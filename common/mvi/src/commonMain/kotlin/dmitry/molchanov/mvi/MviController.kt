package dmitry.molchanov.mvi

abstract class MviController<Model, Event, Intent, SideEffect>(
    private val mviViewModel: MviViewModel<*, Intent>,
    private val mviIntentMapper: MviIntentMapper<Event, Intent>? = null
) {

    private lateinit var mviView: MviView<Model, Event>

    open fun onCreate(mviView: MviView<Model, Event>) {
        this.mviView = mviView

        mviIntentMapper?.let { intentMapper ->
            this.mviView.dispatcher = { event ->
                val intent = intentMapper.map(event)
                mviViewModel.onIntent(intent)
            }
        }

    }

    protected fun render(model: Model) {
        mviView.render(model)
    }

   /* protected fun dispatch(event: Event) {
        mviIntentMapper?.map?.invoke(event)
            ?.let(mviViewModel::onIntent)
    }*/

    protected fun onSideEffect(sideEffect: SideEffect) {
        (this.mviView as? MviSideEffectHandler<SideEffect>)?.onSideEffect(sideEffect)
    }
}
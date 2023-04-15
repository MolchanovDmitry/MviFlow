package dmitry.molchanov.mvi

interface MviSideEffectHandler<SideEffect> {

    fun onSideEffect(sideEffect: SideEffect)
}
package dmitry.molchanov.mvi_kotlin_app

import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.java.KoinJavaComponent

inline fun <reified T> inject(
    qualifier: Qualifier? = null,
     noinline parameters: ParametersDefinition? = null
): Lazy<T> {
    return lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        KoinJavaComponent.get(T::class.java, qualifier, parameters)
    }
}
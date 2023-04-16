package dmity.molchanov.mvi

import androidx.fragment.app.Fragment
import org.koin.android.ext.android.get
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.ParametersHolder
import org.koin.core.qualifier.Qualifier

inline fun <reified T : Any> Fragment.lifecycleInject(
    qualifier: Qualifier? = null,
    mode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED,
    noinline parameters: ParametersDefinition? = null,
): Lazy<T> = lazy(mode) {
    val params = parameters?.invoke()?.values?.toMutableList() ?: mutableListOf()
    params.add(LifecycleFetcher { viewLifecycleOwner.lifecycle })
    get(qualifier) {
        ParametersHolder(params)
    }
}
package dmity.molchanov.mvi

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.ParametersHolder
import org.koin.core.qualifier.Qualifier

inline fun <reified T : Any> Fragment.injectController(
    qualifier: Qualifier? = null,
    mode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED,
    noinline controllerParams: ParametersDefinition? = null,
    noinline viewModelParams: ParametersDefinition? = null,
): Lazy<T> = lazy(mode) {
    val params = controllerParams?.invoke()
        ?.values
        ?.toMutableList()
        ?: mutableListOf()
    params.add(LifecycleFetcher { viewLifecycleOwner.lifecycle })
    params.add(this)
    viewModelParams?.invoke()
        ?.values
        ?.let(::ViewModelParams)
        ?.let(params::add)
    get(qualifier) {
        ParametersHolder(params)
    }
}

inline fun <reified T : ViewModel> ParametersHolder.fetchViewModel(): T {
    val viewModelParams = getOrNull<ViewModelParams>()
    return get<Fragment>().getViewModel<T>(parameters = viewModelParams.toParamHolderOrNull())
}

fun ViewModelParams?.toParamHolderOrNull(): (() -> ParametersHolder)? {
    return this?.params?.toMutableList()?.let {
        { ParametersHolder(it) }
    }
}

class ViewModelParams(val params: List<Any?>)

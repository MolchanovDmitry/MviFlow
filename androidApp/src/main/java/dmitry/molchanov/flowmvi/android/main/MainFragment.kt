package dmitry.molchanov.flowmvi.android.main

import android.content.ComponentCallbacks
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dmitry.molchanov.flowmvi.android.R
import dmitry.molchanov.flowmvi.android.statesToModel
import dmitry.molchanov.presentation.MainVM
import dmitry.molchanov.presentation.main.MainViewModel
import dmitry.molchanov.util.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.ParametersHolder
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier

class MainFragment(private val onItemClick: (Long) -> Unit) : Fragment(R.layout.todo_list) {

    private val controller by mviInject<MainController> {
        parametersOf(onItemClick)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller.onCreate(view, onItemClick)
    }

}

inline fun <reified T : Any> ComponentCallbacks.inject(
    qualifier: Qualifier? = null,
    mode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED,
    noinline parameters: ParametersDefinition? = null,
) = lazy(mode) { get<T>(qualifier, parameters) }

context(Fragment)
        inline fun <reified T : Any> ComponentCallbacks.mviInject(
    qualifier: Qualifier? = null,
    mode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED,
    noinline parameters: ParametersDefinition? = null,
): Lazy<T> = lazy(mode) {
    val params = parameters?.invoke()?.values?.toMutableList() ?: mutableListOf()
    params.add(lifecycle)
    params.add(viewLifecycleOwner)
    get(qualifier) {
        ParametersHolder(params)
    }
}
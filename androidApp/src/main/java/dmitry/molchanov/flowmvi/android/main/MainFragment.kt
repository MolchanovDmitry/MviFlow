package dmitry.molchanov.flowmvi.android.main

import android.content.ComponentCallbacks
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dmitry.molchanov.flowmvi.android.R
import dmitry.molchanov.presentation.main.MainView
import org.koin.android.ext.android.get
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
        val mainView: MainView = MainViewImpl(view)
        controller.onCreate(mainView)
    }

}

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
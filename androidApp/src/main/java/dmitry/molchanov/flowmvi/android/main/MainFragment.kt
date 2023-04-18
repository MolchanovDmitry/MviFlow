package dmitry.molchanov.flowmvi.android.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dmitry.molchanov.flowmvi.android.R
import dmitry.molchanov.presentation.main.MainView
import dmity.molchanov.mvi.injectController
import org.koin.core.parameter.parametersOf

class MainFragment(private val onItemClick: (Long) -> Unit) : Fragment(R.layout.todo_list) {

    private val controller by injectController<MainController>(
        controllerParams = { parametersOf(onItemClick) }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller.onCreate(MainViewImpl(view))
    }
}
package dmitry.molchanov.flowmvi.android.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dmitry.molchanov.flowmvi.android.R
import dmitry.molchanov.presentation.MainVM
import dmitry.molchanov.presentation.main.MainView
import dmity.molchanov.mvi.lifecycleInject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class MainFragment(private val onItemClick: (Long) -> Unit) : Fragment(R.layout.todo_list) {

    private val controller by lifecycleInject<MainController> {
        parametersOf(onItemClick, getViewModel<MainVM>())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainView: MainView = MainViewImpl(view)
        controller.onCreate(mainView)
    }
}
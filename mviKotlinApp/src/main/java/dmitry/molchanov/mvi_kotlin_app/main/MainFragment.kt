package dmitry.molchanov.mvi_kotlin_app.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.arkivanov.essenty.instancekeeper.instanceKeeper
import com.arkivanov.essenty.lifecycle.essentyLifecycle
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import dmitry.molchanov.mvi_kotlin_app.R
import dmitry.molchanov.mvi_kotlin_app.domain.main.MainController
import dmitry.molchanov.mvi_kotlin_app.domain.main.store.AddStore
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainFragment(private val onItemSelected: (id: Long) -> Unit) : Fragment(R.layout.todo_list) {

    private val controller: MainController by inject{
        parametersOf(
            essentyLifecycle(),
            onItemSelected,
            instanceKeeper().getStore { inject<AddStore>().value })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller.onViewCreated(MainViewImpl(view), viewLifecycleOwner.essentyLifecycle())
    }

    fun onItemChanged(id: Long, text: String, isDone: Boolean) {
        controller.onItemChanged(id = id, text, isDone)
    }

    fun onItemDeleted(id: Long) {
        controller.onItemDeleted(id = id)
    }
}

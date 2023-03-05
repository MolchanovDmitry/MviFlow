package dmitry.molchanov.mvi_kotlin_app.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.arkivanov.essenty.instancekeeper.instanceKeeper
import com.arkivanov.essenty.lifecycle.essentyLifecycle
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dmitry.molchanov.mvi_kotlin_app.R
import dmitry.molchanov.mvi_kotlin_app.domain.TodoDispatchers
import dmitry.molchanov.mvi_kotlin_app.domain.TodoItem
import dmitry.molchanov.mvi_kotlin_app.domain.main.MainController

class MainFragment(
    private val storeFactory: StoreFactory,
    private val dispatchers: TodoDispatchers,
    private val onItemSelected: (id: String) -> Unit,
) : Fragment(R.layout.todo_list) {

    private lateinit var controller: MainController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        controller =
            MainController(
                storeFactory = storeFactory,
                lifecycle = essentyLifecycle(),
                instanceKeeper = instanceKeeper(),
                dispatchers = dispatchers,
                onItemSelected = onItemSelected
            )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controller.onViewCreated(MainViewImpl(view), viewLifecycleOwner.essentyLifecycle())
    }

    fun onItemChanged(id: String, data: TodoItem.Data) {
        controller.onItemChanged(id = id, data = data)
    }

    fun onItemDeleted(id: String) {
        controller.onItemDeleted(id = id)
    }
}

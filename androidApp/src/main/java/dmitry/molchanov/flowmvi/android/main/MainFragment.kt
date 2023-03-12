package dmitry.molchanov.flowmvi.android.main

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
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment(private val onItemClick: (Long) -> Unit) : Fragment(R.layout.todo_list) {

    private val dispatchers by inject<Dispatchers>()
    private val vm: MainViewModel by viewModel<MainVM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainView = MainViewImpl(view, MainScreenEventHandler(vm)::handle)

        vm.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .map(statesToModel) // маппер из состояния вью модели в модель для отображения,
            .flowOn(dispatchers.io) // мы имеем возможность маппить в io потоке одной строчкой
            .onEach(mainView::render)
            .launchIn(viewLifecycleOwner.lifecycleScope)

        vm.sideEffect
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { effect ->
                when (effect) {
                    MainViewModel.EmptyAddText -> showMessage("Пустой текст")
                    MainViewModel.TodoItemNotFound -> showMessage("Элемент не найден")
                    is MainViewModel.ItemClick -> onItemClick.invoke(effect.todoItem.id)
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showMessage(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }
}

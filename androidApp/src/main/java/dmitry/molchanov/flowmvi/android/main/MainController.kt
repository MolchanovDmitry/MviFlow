package dmitry.molchanov.flowmvi.android.main

import android.view.View
import androidx.lifecycle.*
import dmitry.molchanov.flowmvi.android.statesToModel
import dmitry.molchanov.mvi.MviViewModel
import dmitry.molchanov.presentation.MainVM
import dmitry.molchanov.presentation.main.MainViewModel
import dmitry.molchanov.util.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainController(
    private val lifecycle: Lifecycle,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: MainVM,
    private val dispatchers: Dispatchers,
) {

    fun onCreate(view: View, onItemClick: (Long) -> Unit) {
        val mainView = MainViewImpl(view, MainScreenEventHandler(viewModel)::handle)

        viewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .map(statesToModel) // маппер из состояния вью модели в модель для отображения,
            .flowOn(dispatchers.io) // мы имеем возможность маппить в io потоке одной строчкой
            .onEach(mainView::render)
            .launchIn(lifecycleOwner.lifecycleScope)

        viewModel.sideEffect
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { effect ->
                when (effect) {
                    MainViewModel.EmptyAddText -> mainView.showMessage("Пустой текст")
                    MainViewModel.TodoItemNotFound -> mainView.showMessage("Элемент не найден")
                    is MainViewModel.ItemClick -> onItemClick.invoke(effect.todoItem.id)
                }
            }
            .launchIn(lifecycleOwner.lifecycleScope)
    }

}
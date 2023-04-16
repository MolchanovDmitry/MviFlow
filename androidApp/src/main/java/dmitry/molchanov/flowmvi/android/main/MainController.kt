package dmitry.molchanov.flowmvi.android.main

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import dmitry.molchanov.flowmvi.android.sideEffectMapper
import dmitry.molchanov.flowmvi.android.statesToModel
import dmitry.molchanov.mvi.MviController
import dmitry.molchanov.mvi.MviView
import dmitry.molchanov.presentation.MainVM
import dmitry.molchanov.presentation.main.MainView.Effect
import dmitry.molchanov.presentation.main.MainView.Event
import dmitry.molchanov.presentation.main.MainView.Model
import dmitry.molchanov.presentation.main.MainViewModel.Intent
import dmitry.molchanov.presentation.main.MainViewModel.ItemClick
import dmitry.molchanov.util.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach

class MainController(
    private val lifecycle: Lifecycle,
    private val viewModel: MainVM,
    private val dispatchers: Dispatchers,
    private val onItemClick: (Long) -> Unit,
    mainScreenEventHandler: MainScreenIntentMapper
) : MviController<Model, Event, Intent, Effect>(viewModel, mainScreenEventHandler) {

    override fun onCreate(mviView: MviView<Model, Event>) {
        super.onCreate(mviView)

        viewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { println("112233 kokoke $it") }
            .map(statesToModel)
            .flowOn(dispatchers.io)
            .onEach(::render)
            .launchIn(lifecycle.coroutineScope)


        viewModel.sideEffect
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { vmSideEffect ->
                if (vmSideEffect is ItemClick) {
                    onItemClick.invoke(vmSideEffect.todoItem.id)
                }
            }
            .mapNotNull(sideEffectMapper)
            .onEach(::onSideEffect)
            .launchIn(lifecycle.coroutineScope)
    }

}
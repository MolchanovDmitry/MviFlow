package dmitry.molchanov.flowmvi.android.main

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import dmitry.molchanov.flowmvi.android.sideEffectMapper
import dmitry.molchanov.flowmvi.android.statesToModel
import dmitry.molchanov.mvi.MviController
import dmitry.molchanov.mvi.MviView
import dmitry.molchanov.presentation.MainVM
import dmitry.molchanov.presentation.main.MainStore.Intent
import dmitry.molchanov.presentation.main.MainStore.ItemClick
import dmitry.molchanov.presentation.main.MainView.Effect
import dmitry.molchanov.presentation.main.MainView.Event
import dmitry.molchanov.presentation.main.MainView.Model
import dmitry.molchanov.util.Dispatchers
import dmity.molchanov.mvi.LifecycleFetcher
import dmity.molchanov.mvi.lifecycle
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach

class MainController(
    private val lifecycleFetcher: LifecycleFetcher,
    private val viewModel: MainVM,
    private val dispatchers: Dispatchers,
    private val onItemClick: (Long) -> Unit,
    mainScreenEventHandler: MainScreenIntentMapper
) : MviController<Model, Event, Intent, Effect>(viewModel, mainScreenEventHandler) {

    override fun onCreate(mviView: MviView<Model, Event>) {
        super.onCreate(mviView)

        viewModel.state
            .flowWithLifecycle(lifecycleFetcher.lifecycle, Lifecycle.State.STARTED)
            .map(statesToModel)
            .flowOn(dispatchers.io)
            .onEach(::render)
            .launchIn(lifecycleFetcher.lifecycle.coroutineScope)

        viewModel.sideEffect
            .flowWithLifecycle(lifecycleFetcher.lifecycle, Lifecycle.State.STARTED)
            .onEach { vmSideEffect ->
                if (vmSideEffect is ItemClick) {
                    onItemClick.invoke(vmSideEffect.todoItem.id)
                }
            }
            .mapNotNull(sideEffectMapper)
            .onEach(::onSideEffect)
            .launchIn(lifecycleFetcher.lifecycle.coroutineScope)
    }

}
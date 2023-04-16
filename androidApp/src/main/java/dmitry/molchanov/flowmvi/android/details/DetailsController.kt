package dmitry.molchanov.flowmvi.android.details

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import dmitry.molchanov.mvi.MviController
import dmitry.molchanov.mvi.MviView
import dmitry.molchanov.presentation.details.DetailsView.Event
import dmitry.molchanov.presentation.details.DetailsView.Model
import dmitry.molchanov.presentation.details.DetailsViewModel
import dmitry.molchanov.presentation.details.DetailsViewModel.Intent
import dmitry.molchanov.presentation.details.DetailsViewModel.ItemState
import dmitry.molchanov.util.Dispatchers
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class DetailsController(
    private val lifecycle: Lifecycle,
    private val detailsViewModel: DetailsViewModel,
    private val dispatchers: Dispatchers,
    detailsViewEventHandler: DetailsViewEventHandler
) : MviController<Model, Event, Intent, Unit>(detailsViewModel, detailsViewEventHandler) {

    override fun onCreate(mviView: MviView<Model, Event>) {
        super.onCreate(mviView)


        detailsViewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .filterIsInstance<ItemState>()
            .onEach { println("112233 state = $it") }
            .map { state -> Model(text = state.todoItem.text, isDone = state.todoItem.isDone) }
            .onEach { println("112233 model = $it") }
            .flowOn(dispatchers.io)
            .onEach(::render)
            .launchIn(lifecycle.coroutineScope)
    }
}
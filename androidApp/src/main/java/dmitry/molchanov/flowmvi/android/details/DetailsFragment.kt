package dmitry.molchanov.flowmvi.android.details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dmitry.molchanov.flowmvi.android.R
import dmitry.molchanov.presentation.DetailsVM
import dmitry.molchanov.presentation.details.DetailsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsFragment : Fragment(R.layout.todo_details) {

    private val vm: DetailsViewModel by viewModel<DetailsVM> {
        parametersOf(requireArguments().getLong(KEY_ARGUMENTS))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val detailView = DetailsViewImpl(view, DetailsViewEventHandler(vm)::handle)
        vm.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                when(state){
                    DetailsViewModel.FinisState -> parentFragmentManager.popBackStack()
                    is DetailsViewModel.ItemState -> detailView.render(state.todoItem)
                    else -> Unit
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    fun setArguments(itemId: Long): DetailsFragment {
        arguments = bundleOf(KEY_ARGUMENTS to itemId)
        return this
    }

    private companion object {
        private const val KEY_ARGUMENTS = "ARGUMENTS"
    }
}

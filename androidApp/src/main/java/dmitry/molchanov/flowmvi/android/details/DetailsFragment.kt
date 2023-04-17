package dmitry.molchanov.flowmvi.android.details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import dmitry.molchanov.flowmvi.android.R
import dmity.molchanov.mvi.injectController
import org.koin.core.parameter.parametersOf

class DetailsFragment(private val onItemDeleted: () -> Unit) : Fragment(R.layout.todo_details) {

    private val argument: Long
        get() = requireArguments().getLong(KEY_ARGUMENTS)

    private val detailsController by injectController<DetailsController>(
        controllerParams = { parametersOf(onItemDeleted) },
        viewModelParams = { parametersOf(argument) },
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsController.onCreate(DetailsViewImpl(view))
    }

    fun setArguments(itemId: Long): DetailsFragment {
        arguments = bundleOf(KEY_ARGUMENTS to itemId)
        return this
    }

    private companion object {
        private const val KEY_ARGUMENTS = "ARGUMENTS"
    }
}

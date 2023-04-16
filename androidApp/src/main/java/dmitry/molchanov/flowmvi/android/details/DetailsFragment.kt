package dmitry.molchanov.flowmvi.android.details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import dmitry.molchanov.flowmvi.android.R
import dmity.molchanov.mvi.lifecycleInject
import org.koin.core.parameter.parametersOf

class DetailsFragment : Fragment(R.layout.todo_details) {

    private val detailsController by lifecycleInject<DetailsController>{
        parametersOf(requireArguments().getLong(KEY_ARGUMENTS))
    }

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

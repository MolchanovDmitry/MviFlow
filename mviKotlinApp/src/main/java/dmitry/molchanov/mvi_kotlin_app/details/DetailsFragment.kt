package dmitry.molchanov.mvi_kotlin_app.details

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.arkivanov.essenty.lifecycle.essentyLifecycle
import dmitry.molchanov.model.TodoItem
import dmitry.molchanov.mvi_kotlin_app.R
import dmitry.molchanov.mvi_kotlin_app.domain.TodoDispatchers
import dmitry.molchanov.mvi_kotlin_app.domain.details.DetailsController
import dmitry.molchanov.mvi_kotlin_app.domain.details.store.DetailsStore
import dmitry.molchanov.mvi_kotlin_app.inject
import kotlinx.parcelize.Parcelize
import org.koin.core.parameter.parametersOf

class DetailsFragment(
    private val onItemChanged: (todoItem: TodoItem) -> Unit,
    private val onItemDeleted: (id: Long) -> Unit,
) : Fragment(R.layout.todo_details) {

    private lateinit var controller: DetailsController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args =
            requireArguments()
                .apply { classLoader = Arguments::class.java.classLoader }
                .getParcelable<Arguments>(KEY_ARGUMENTS) as Arguments

        controller =
            DetailsController(
                lifecycle = essentyLifecycle(),
                dispatchers = inject<TodoDispatchers>().value,
                onItemChanged = onItemChanged,
                onItemDeleted = onItemDeleted,
                detailsStore = inject<DetailsStore> { parametersOf(args.itemId) }.value,
            )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controller.onViewCreated(DetailsViewImpl(view), viewLifecycleOwner.essentyLifecycle())
    }

    fun setArguments(itemId: Long): DetailsFragment {
        arguments = bundleOf(KEY_ARGUMENTS to Arguments(itemId = itemId))

        return this
    }

    private companion object {
        private const val KEY_ARGUMENTS = "ARGUMENTS"
    }

    @Parcelize
    private class Arguments(val itemId: Long) : Parcelable
}

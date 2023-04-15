package dmitry.molchanov.flowmvi.android.main

import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import dmitry.molchanov.flowmvi.android.R
import dmitry.molchanov.flowmvi.android.SimpleTextWatcher
import dmitry.molchanov.flowmvi.android.getViewById
import dmitry.molchanov.flowmvi.android.setTextCompat
import dmitry.molchanov.presentation.main.MainView
import dmitry.molchanov.presentation.main.MainView.Event
import dmitry.molchanov.presentation.main.MainView.Model

class MainViewImpl(
    private val root: View,
    override val dispatch: (Event) -> Unit
) : MainView<Model, Event> {

    private val adapter =
        ListAdapter(
            onItemClick = { dispatch(Event.ItemClicked(it)) },
            onItemDoneClick = { dispatch(Event.ItemDoneClicked(it)) },
            onItemDeleteClick = { dispatch(Event.ItemDeleteClicked(it)) },
        )

    private val textWatcher =
        object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                dispatch(Event.TextChanged(s.toString()))
            }
        }

    private val editText = root.getViewById<EditText>(R.id.todo_edit)

    init {
        root.getViewById<RecyclerView>(R.id.recycler_view).adapter = adapter

        root.getViewById<View>(R.id.add_button).setOnClickListener {
            dispatch(Event.AddClicked)
        }

        editText.addTextChangedListener(textWatcher)
    }

    fun showMessage(text: String) {
        Toast.makeText(root.context, text, Toast.LENGTH_SHORT).show()
    }

    override fun render(model: Model) {
        adapter.items = model.items
        editText.setTextCompat(model.text, textWatcher)
    }
}

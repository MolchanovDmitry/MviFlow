package dmitry.molchanov.mvi_kotlin_app.main

import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.arkivanov.mvikotlin.core.utils.diff
import com.arkivanov.mvikotlin.core.view.BaseMviView
import com.arkivanov.mvikotlin.core.view.ViewRenderer
import dmitry.molchanov.mvi_kotlin_app.R
import dmitry.molchanov.mvi_kotlin_app.SimpleTextWatcher
import dmitry.molchanov.mvi_kotlin_app.domain.main.MainView
import dmitry.molchanov.mvi_kotlin_app.domain.main.MainView.Event
import dmitry.molchanov.mvi_kotlin_app.domain.main.MainView.Model
import dmitry.molchanov.mvi_kotlin_app.getViewById
import dmitry.molchanov.mvi_kotlin_app.setTextCompat

class MainViewImpl(root: View) : BaseMviView<Model, Event>(), MainView {

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

    override val renderer: ViewRenderer<Model> =
        diff {
            diff(get = Model::items, compare = { a, b -> a === b }) { adapter.items = it }
            diff(get = Model::text) { editText.setTextCompat(it, textWatcher) }
        }

    init {
        root.getViewById<RecyclerView>(R.id.recycler_view).adapter = adapter

        root.getViewById<View>(R.id.add_button).setOnClickListener {
            dispatch(Event.AddClicked)
        }

        editText.addTextChangedListener(textWatcher)
    }
}

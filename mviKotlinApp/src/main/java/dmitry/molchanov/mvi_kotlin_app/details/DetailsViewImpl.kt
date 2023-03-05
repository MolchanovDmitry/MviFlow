package dmitry.molchanov.mvi_kotlin_app.details

import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.arkivanov.mvikotlin.core.utils.diff
import com.arkivanov.mvikotlin.core.view.BaseMviView
import com.arkivanov.mvikotlin.core.view.ViewRenderer
import dmitry.molchanov.mvi_kotlin_app.R
import dmitry.molchanov.mvi_kotlin_app.SimpleTextWatcher
import dmitry.molchanov.mvi_kotlin_app.domain.details.DetailsView
import dmitry.molchanov.mvi_kotlin_app.domain.details.DetailsView.Event
import dmitry.molchanov.mvi_kotlin_app.domain.details.DetailsView.Model
import dmitry.molchanov.mvi_kotlin_app.getViewById
import dmitry.molchanov.mvi_kotlin_app.setTextCompat

class DetailsViewImpl(root: View) : BaseMviView<Model, Event>(), DetailsView {

    private val textWatcher =
        object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                dispatch(Event.TextChanged(text = s.toString()))
            }
        }

    private val editText = root.getViewById<EditText>(R.id.edit_text)
    private val checkBox = root.getViewById<CheckBox>(R.id.check_completed)

    override val renderer: ViewRenderer<Model> =
        diff {
            diff(Model::text) { editText.setTextCompat(it, textWatcher) }
            diff(get = Model::isDone, set = checkBox::setChecked)
        }

    init {
        root.getViewById<Toolbar>(R.id.toolbar).apply {
            inflateMenu(R.menu.details)
            setOnMenuItemClickListener(::onMenuItemClick)
        }

        editText.addTextChangedListener(textWatcher)
        checkBox.setOnClickListener { dispatch(Event.DoneClicked) }
    }

    private fun onMenuItemClick(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_delete -> {
                dispatch(Event.DeleteClicked)
                true
            }

            else -> false
        }
}

package dmitry.molchanov.flowmvi.android.details

import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import dmitry.molchanov.flowmvi.android.R
import dmitry.molchanov.flowmvi.android.SimpleTextWatcher
import dmitry.molchanov.flowmvi.android.getViewById
import dmitry.molchanov.flowmvi.android.setTextCompat
import dmitry.molchanov.presentation.details.DetailsView

class DetailsViewImpl(root: View) : DetailsView() {

    private val textWatcher =
        object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                dispatch(Event.TextChanged(text = s.toString()))
            }
        }

    private val editText = root.getViewById<EditText>(R.id.edit_text)
    private val checkBox = root.getViewById<CheckBox>(R.id.check_completed)

    init {
        root.getViewById<Toolbar>(R.id.toolbar).apply {
            inflateMenu(R.menu.details)
            setOnMenuItemClickListener(::onMenuItemClick)
        }

        editText.addTextChangedListener(textWatcher)
        checkBox.setOnClickListener {
            dispatch(Event.DoneClicked)
        }
    }

    override fun render(model: Model) {
        editText.setTextCompat(model.text, textWatcher)
        checkBox.isChecked = model.isDone
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

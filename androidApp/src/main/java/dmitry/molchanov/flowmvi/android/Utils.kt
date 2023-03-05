package dmitry.molchanov.flowmvi.android

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.annotation.IdRes

val Context.app: App get() = applicationContext as App

fun <T : Any> T?.requireNotNull(): T = requireNotNull(this)

fun <T : View> View.getViewById(@IdRes id: Int): T = findViewById<T>(id).requireNotNull()

fun EditText.setTextCompat(text: CharSequence, textWatcher: TextWatcher? = null) {
    val savedSelectionStart = selectionStart
    val savedSelectionEnd = selectionEnd
    textWatcher?.also(::removeTextChangedListener)
    setText(text)
    textWatcher?.also(::addTextChangedListener)
    if (savedSelectionEnd <= text.length) {
        setSelection(savedSelectionStart, savedSelectionEnd)
    } else {
        setSelection(text.length)
    }
}

open class SimpleTextWatcher : TextWatcher {
    override fun afterTextChanged(s: Editable) {
        // no-op
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        // no-op
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        // no-op
    }
}

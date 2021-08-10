package ru.netology.nmedia.util

import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


fun EditText.showSoftKeyboard() {
    this.requestFocus()
    (this.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
        .showSoftInput(this, InputMethodManager.SHOW_FORCED)
}

fun EditText.hideKeyboard() {
    this.clearFocus()
    (this.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(this.windowToken, 0)
}

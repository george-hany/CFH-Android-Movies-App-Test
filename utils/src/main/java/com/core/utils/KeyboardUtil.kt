package com.core.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideSoftKeyboard() {
    val inputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

    if (inputMethodManager.isActive) {
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}

fun Context.hideSoftKeyboard() {
    val inputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = getActivity()?.currentFocus
    if (view == null) view = View(this)
    if (inputMethodManager.isActive) {
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

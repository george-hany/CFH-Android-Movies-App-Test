package com.core.utils

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity

fun Context?.getActivity(): AppCompatActivity? {
    if (this == null) {
        return null
    } else if (this is ContextWrapper) {
        return if (this is AppCompatActivity) {
            this
        } else {
            this.baseContext.getActivity()
        }
    }
    return null
}

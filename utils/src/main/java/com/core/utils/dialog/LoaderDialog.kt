package com.core.utils.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.core.resources.databinding.DialogLoaderBinding
import com.core.utils.R

object LoaderDialog {
    fun Context.showLoaderDialog(): Dialog {
        val dialog = Dialog(this, R.style.PauseDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding: DialogLoaderBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(this),
                R.layout.dialog_loader,
                null,
                false,
            )
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val width = (this.resources.displayMetrics.widthPixels * 0.9).toInt()
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(false)
        dialog.window?.setLayout(
            width,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
        return dialog
    }
}

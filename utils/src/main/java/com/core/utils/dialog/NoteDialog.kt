package com.core.utils.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.core.resources.databinding.DialogNoteBinding
import com.core.utils.R

object NoteDialog {
    fun Context.showMessageDialog(
        note: Any,
        onDismiss: () -> Unit,
    ) {
        val dialog = Dialog(this, R.style.PauseDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding: DialogNoteBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(this),
                R.layout.dialog_note,
                null,
                false,
            )
        binding.note.text =
            if (note is Int) {
                getString(note)
            } else {
                note as String
            }
        binding.ok.setOnClickListener {
            onDismiss()
            dialog.dismiss()
        }
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val width = (this.resources.displayMetrics.widthPixels * 0.9).toInt()
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(false)
        dialog.window?.setLayout(
            width,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
        dialog.show()
    }
}

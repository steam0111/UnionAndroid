package com.itrocket.union.utils.fragment

import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.itrocket.union.R

fun Fragment.displayError(errorText: Int) {
    val alertDialog = AlertDialog.Builder(requireContext())
        .setView(R.layout.dialog_error)
        .create()
    alertDialog.show()

    val title = alertDialog.findViewById<TextView>(R.id.textViewTitle)
    val cancelText = alertDialog.findViewById<TextView>(R.id.textViewOk)

    updateStyle(title = title, cancelText = cancelText, context = requireContext())

    title?.text = requireContext().getString(errorText)
    cancelText?.setOnClickListener {
        alertDialog.dismiss()
    }
}

private fun updateStyle(title: TextView?, cancelText: TextView?, context: Context) {
    val typedArray = getThemeAttrs(context)

    val titleStyle = typedArray.getResourceId(
        R.styleable.CoreTheme_dialogTitleTextErrorStyle,
        R.style.TextAppearance_AppCompat_Body2
    )

    val cancelStyle = typedArray.getResourceId(
        R.styleable.CoreTheme_dialogCancelTextErrorStyle,
        R.style.TextAppearance_AppCompat_Body1_DialogCancelStyle
    )

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        title?.setTextAppearance(titleStyle)
        cancelText?.setTextAppearance(cancelStyle)
    } else {
        title?.setTextAppearance(context, titleStyle)
        cancelText?.setTextAppearance(context, cancelStyle)
    }
    typedArray.recycle()
}

private fun getThemeAttrs(context: Context): TypedArray {
    return context.theme.obtainStyledAttributes(
        R.style.CoreTheme, intArrayOf(
            R.attr.dialogCancelTextErrorStyle,
            R.attr.dialogTitleTextErrorStyle,
        )
    )
}
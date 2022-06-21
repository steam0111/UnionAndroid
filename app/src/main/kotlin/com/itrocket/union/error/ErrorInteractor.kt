package com.itrocket.union.error

import android.content.Context
import com.itrocket.union.R

class ErrorInteractor(val context: Context) {

    fun getDefaultError() = context.getString(R.string.common_error)
}
package com.itrocket.union.error

import android.content.Context
import androidx.annotation.StringRes
import com.itrocket.union.R
import com.itrocket.union.network.HttpException

class ErrorInteractor(val context: Context) {

    fun getDefaultError() = context.getString(R.string.common_error)

    fun getTextMessage(throwable: Throwable): String {
        return when (throwable) {
            is HttpException -> {
                throwable.getFormattedMessage() ?: getDefaultError()
            }
            else -> getDefaultError()
        }
    }

    fun getExceptionMessageByResId(@StringRes messageId: Int): String{
        return context.getString(messageId)
    }
}
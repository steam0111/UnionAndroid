package com.itrocket.union.error

import android.content.Context
import androidx.annotation.StringRes
import com.itrocket.union.R
import com.itrocket.union.network.HttpException
import com.itrocket.union.utils.ifBlankOrNull

class ErrorInteractor(val context: Context) {

    fun getDefaultError() = context.getString(R.string.common_error)

    fun getTextMessage(throwable: Throwable): String {
        return when (throwable) {
            is HttpException -> {
                throwable.getFormattedMessage().ifBlankOrNull {
                    getDefaultError()
                }
            }
            else -> throwable.message.ifBlankOrNull { getDefaultError() }
        }
    }

    fun getExceptionMessageByResId(@StringRes messageId: Int): String {
        return context.getString(messageId)
    }

    fun getThrowableByResId(@StringRes messageId: Int) : Throwable {
        return Throwable(context.getString(messageId))
    }
}
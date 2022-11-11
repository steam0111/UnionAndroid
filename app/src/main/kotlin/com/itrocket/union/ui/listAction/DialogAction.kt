package com.itrocket.union.ui.listAction

import androidx.annotation.StringRes

data class DialogAction(
    val type: DialogActionType,
    @StringRes val actionTextId: Int
)

enum class DialogActionType {
    WRITE_OFF
}
package com.itrocket.union.documents.domain.entity

import androidx.annotation.StringRes
import com.itrocket.union.R

enum class ObjectAction(@StringRes val textId: Int) {
    OPEN_CARD(R.string.open_card),
    DELETE_FROM_LIST(R.string.delete_from_list)
}
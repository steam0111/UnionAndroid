package com.itrocket.union.documents.domain.entity

import android.os.Parcelable
import androidx.annotation.StringRes
import com.itrocket.union.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class ActionsWithIdentifyObjects(@StringRes val textId: Int) : Parcelable {
    OPEN_CARD(R.string.open_card),
    DELETE_FROM_LIST(R.string.delete_from_list)
}
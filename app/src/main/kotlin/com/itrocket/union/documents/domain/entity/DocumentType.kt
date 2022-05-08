package com.itrocket.union.documents.domain.entity

import android.os.Parcelable
import androidx.annotation.StringRes
import com.itrocket.union.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class DocumentTypeDomain(@StringRes val titleId: Int) : Parcelable {
    EXTRADITION(titleId = R.string.main_issue),
    RETURN(titleId = R.string.main_return),
    MOVING(titleId = R.string.main_moved),
    WRITE_OFF(titleId = R.string.main_write_off),
    COMMISSIONING(titleId = R.string.main_commissioning),
    ALL(titleId = R.string.common_empty)
}
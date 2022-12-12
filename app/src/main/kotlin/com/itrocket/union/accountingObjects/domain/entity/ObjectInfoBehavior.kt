package com.itrocket.union.accountingObjects.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class ObjectInfoBehavior : Parcelable {
    DEFAULT, LABEL_TYPE
}
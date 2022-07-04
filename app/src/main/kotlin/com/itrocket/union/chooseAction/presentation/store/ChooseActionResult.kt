package com.itrocket.union.chooseAction.presentation.store

import android.os.Parcelable
import com.itrocket.union.documents.domain.entity.ObjectType
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChooseActionResult(val type: ObjectType) : Parcelable
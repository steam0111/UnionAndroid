package com.itrocket.union.labelType.presentation.store

import android.os.Parcelable
import com.itrocket.union.labelType.domain.entity.LabelTypeDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class LabelTypeResult(val labelTypeId: String) : Parcelable
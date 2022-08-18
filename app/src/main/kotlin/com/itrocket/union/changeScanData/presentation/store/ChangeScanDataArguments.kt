package com.itrocket.union.changeScanData.presentation.store

import android.os.Parcelable
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.changeScanData.domain.entity.ChangeScanType
import com.itrocket.union.documents.domain.entity.ObjectType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChangeScanDataArguments(
    val changeScanType: ChangeScanType,
    val scanValue: String?,
    val entityId: String
) : Parcelable
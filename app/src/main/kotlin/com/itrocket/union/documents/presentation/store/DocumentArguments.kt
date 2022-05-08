package com.itrocket.union.documents.presentation.store

import android.os.Parcelable
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DocumentArguments(val type: DocumentTypeDomain) : Parcelable
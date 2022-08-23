package com.itrocket.union.transit.presentation.store

import android.os.Parcelable
import com.itrocket.union.documents.domain.entity.DocumentDomain
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransitArguments(val transit: DocumentDomain) : Parcelable
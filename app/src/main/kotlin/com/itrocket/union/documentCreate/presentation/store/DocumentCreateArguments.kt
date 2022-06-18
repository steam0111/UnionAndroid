package com.itrocket.union.documentCreate.presentation.store

import android.os.Parcelable
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.documents.domain.entity.ObjectType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DocumentCreateArguments(val document: DocumentDomain) :
    Parcelable
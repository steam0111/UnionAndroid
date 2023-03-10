package com.itrocket.union.documentCreate.presentation.store

import android.os.Parcelable
import com.itrocket.union.documents.domain.entity.DocumentDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class DocumentCreateArguments(val document: DocumentDomain) :
    Parcelable
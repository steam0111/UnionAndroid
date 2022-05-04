package com.itrocket.union.documentsMenu.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.union.documentsMenu.domain.entity.DocumentMenuDomain

interface DocumentMenuStore :
    Store<DocumentMenuStore.Intent, DocumentMenuStore.State, DocumentMenuStore.Label> {

    sealed class Intent {
        data class OnDocumentClicked(val item: DocumentMenuDomain) : Intent()
        object OnProfileClicked : Intent()
    }

    data class State(
        val documents: List<DocumentMenuDomain> = listOf(),
        val userName: String = ""
    )

    sealed class Label
}
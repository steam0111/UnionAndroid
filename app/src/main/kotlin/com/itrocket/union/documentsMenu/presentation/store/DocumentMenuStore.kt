package com.itrocket.union.documentsMenu.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.union.R
import com.itrocket.union.documentsMenu.domain.entity.DocumentMenuDomain
import com.itrocket.union.documentsMenu.presentation.view.DocumentMenuComposeFragmentDirections

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

    sealed class Label{
        data class ShowDocumentDetail(val item: DocumentMenuDomain) : Label(),
            ForwardNavigationLabel {
            override val directions: NavDirections
                get() = when (item.titleId) {
                    R.string.main_accounting_object -> DocumentMenuComposeFragmentDirections.toAccountingObjects()
                    else -> DocumentMenuComposeFragmentDirections.toAccountingObjects()
                }
        }
    }
}
package com.itrocket.union.documentsMenu.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.union.R
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.documents.presentation.store.DocumentArguments
import com.itrocket.union.documentsMenu.domain.entity.DocumentMenuDomain
import com.itrocket.union.documentsMenu.presentation.view.DocumentMenuComposeFragmentDirections
import com.itrocket.union.nomenclatureGroup.presentation.store.NomenclatureGroupArguments

interface DocumentMenuStore :
    Store<DocumentMenuStore.Intent, DocumentMenuStore.State, DocumentMenuStore.Label> {

    sealed class Intent {
        data class OnDocumentClicked(val item: DocumentMenuDomain) : Intent()
        object OnProfileClicked : Intent()
        object OnLogoutClicked : Intent()
    }

    data class State(
        val documents: List<DocumentMenuDomain> = listOf(),
        val userName: String = "",
        val loading: Boolean = false
    )

    sealed class Label {
        object ShowAuth : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = DocumentMenuComposeFragmentDirections.toAuth()
        }

        data class ShowDocumentDetail(val item: DocumentMenuDomain) : Label(),
            ForwardNavigationLabel {
            override val directions: NavDirections
                get() = when (item.titleId) {
                    R.string.main_accounting_object -> DocumentMenuComposeFragmentDirections.toAccountingObjects()
                    R.string.main_reserves -> DocumentMenuComposeFragmentDirections.toReserves()
                    R.string.main_documents -> DocumentMenuComposeFragmentDirections.toDocuments(
                        DocumentArguments(DocumentTypeDomain.ALL)
                    )
                    R.string.main_commissioning -> DocumentMenuComposeFragmentDirections.toDocuments(
                        DocumentArguments(DocumentTypeDomain.COMMISSIONING)
                    )
                    R.string.main_issue -> DocumentMenuComposeFragmentDirections.toDocuments(
                        DocumentArguments(DocumentTypeDomain.EXTRADITION)
                    )
                    R.string.main_return -> DocumentMenuComposeFragmentDirections.toDocuments(
                        DocumentArguments(DocumentTypeDomain.RETURN)
                    )
                    R.string.main_moved -> DocumentMenuComposeFragmentDirections.toDocuments(
                        DocumentArguments(DocumentTypeDomain.MOVING)
                    )
                    R.string.main_write_off -> DocumentMenuComposeFragmentDirections.toDocuments(
                        DocumentArguments(DocumentTypeDomain.WRITE_OFF)
                    )
                    R.string.nomenclature_group -> DocumentMenuComposeFragmentDirections.toNomenclatureGroup(
                        NomenclatureGroupArguments(0)
                    )
                    else -> DocumentMenuComposeFragmentDirections.toAccountingObjects()
                }
        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}
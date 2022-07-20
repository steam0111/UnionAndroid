package com.itrocket.union.documentsMenu.presentation.view

import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.documentsMenu.DocumentMenuModule.DOCUMENTMENU_VIEW_MODEL_QUALIFIER
import com.itrocket.union.documentsMenu.presentation.store.DocumentMenuStore

class DocumentMenuComposeFragment :
    BaseComposeFragment<DocumentMenuStore.Intent, DocumentMenuStore.State, DocumentMenuStore.Label>(
        DOCUMENTMENU_VIEW_MODEL_QUALIFIER
    ) {

    @OptIn(ExperimentalFoundationApi::class)
    override fun renderState(
        state: DocumentMenuStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        setContent(composeView, state, appInsets)
    }

    override val onBackPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                accept(DocumentMenuStore.Intent.OnBackClicked)
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    private fun setContent(
        composeView: ComposeView,
        state: DocumentMenuStore.State,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            DocumentMenuScreen(
                state = state,
                appInsets = appInsets,
                onProfileIconClick = {
                    accept(DocumentMenuStore.Intent.OnProfileClicked)
                },
                onDocumentItemClick = {
                    accept(DocumentMenuStore.Intent.OnDocumentClicked(it))
                },
                onLogoutClickListener = {
                    accept(DocumentMenuStore.Intent.OnLogoutClicked)
                },
                onSettingsClickListener = {
                    accept(DocumentMenuStore.Intent.OnSettingsClicked)
                }
            )
        }
    }
}
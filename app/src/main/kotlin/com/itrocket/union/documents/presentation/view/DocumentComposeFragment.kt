package com.itrocket.union.documents.presentation.view

import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.navigation.FragmentResult
import com.itrocket.union.chooseAction.presentation.store.ChooseActionResult
import com.itrocket.union.chooseAction.presentation.view.ChooseActionComposeFragment
import com.itrocket.union.documents.DocumentModule.DOCUMENT_VIEW_MODEL_QUALIFIER
import com.itrocket.union.documents.presentation.store.DocumentStore
import com.itrocket.union.filter.presentation.view.FilterComposeFragment
import com.itrocket.union.selectParams.presentation.store.SelectParamsResult

class DocumentComposeFragment :
    BaseComposeFragment<DocumentStore.Intent, DocumentStore.State, DocumentStore.Label>(
        DOCUMENT_VIEW_MODEL_QUALIFIER
    ) {

    override val onBackPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                accept(DocumentStore.Intent.OnArrowBackClicked)
            }
        }
    }

    override val fragmentResultList: List<FragmentResult>
        get() = listOf(
            FragmentResult(
                resultCode = FilterComposeFragment.FILTER_RESULT_CODE,
                resultLabel = FilterComposeFragment.FILTER_RESULT_LABEL,
                resultAction = {
                    (it as SelectParamsResult?)?.params?.let {
                        accept(DocumentStore.Intent.OnFilterResult(it))
                    }
                }
            ),
            FragmentResult(
                resultCode = ChooseActionComposeFragment.CHOOSE_ACTION_RESULT_CODE,
                resultLabel = ChooseActionComposeFragment.CHOOSE_ACTION_RESULT_LABEL,
                resultAction = {
                    (it as ChooseActionResult?)?.type?.let {
                        accept(DocumentStore.Intent.OnObjectTypeSelected(it))
                    }
                }
            )
        )

    override val navArgs by navArgs<DocumentComposeFragmentArgs>()

    override fun renderState(
        state: DocumentStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            DocumentScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = { accept(DocumentStore.Intent.OnArrowBackClicked) },
                onSearchClickListener = {
                    accept(DocumentStore.Intent.OnSearchClicked)
                },
                onFilterClickListener = {
                    accept(DocumentStore.Intent.OnFilterClicked)
                },
                onDocumentClickListener = {
                    accept(DocumentStore.Intent.OnDocumentClicked(it))
                },
                onCreateRequestClickListener = {
                    accept(DocumentStore.Intent.OnDocumentCreateClicked)
                },
                onDateArrowClickListener = {
                    accept(DocumentStore.Intent.OnDateArrowClicked(it))
                },
                onSearchTextChanged = {
                    accept(DocumentStore.Intent.OnSearchTextChanged(it))
                },
            )
        }
    }
}
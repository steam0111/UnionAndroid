package com.itrocket.union.documentCreate.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.navigation.FragmentResult
import com.itrocket.union.accountingObjects.presentation.store.AccountingObjectResult
import com.itrocket.union.accountingObjects.presentation.view.AccountingObjectComposeFragment
import com.itrocket.union.documentCreate.DocumentCreateModule.DOCUMENTCREATE_VIEW_MODEL_QUALIFIER
import com.itrocket.union.documentCreate.presentation.store.DocumentCreateStore
import com.itrocket.union.selectParams.presentation.store.SelectParamsResult
import com.itrocket.union.selectParams.presentation.view.SelectParamsComposeFragment

class DocumentCreateComposeFragment :
    BaseComposeFragment<DocumentCreateStore.Intent, DocumentCreateStore.State, DocumentCreateStore.Label>(
        DOCUMENTCREATE_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<DocumentCreateComposeFragmentArgs>()

    override val fragmentResultList: List<FragmentResult>
        get() = listOf(
            FragmentResult(
                resultCode = SelectParamsComposeFragment.SELECT_PARAMS_RESULT_CODE,
                resultLabel = SelectParamsComposeFragment.SELECT_PARAMS_RESULT,
                resultAction = {
                    (it as SelectParamsResult?)?.params?.let {
                        accept(
                            DocumentCreateStore.Intent.OnParamsChanged(it)
                        )
                    }
                }
            ),
            FragmentResult(
                resultCode = AccountingObjectComposeFragment.ACCOUNTING_OBJECT_RESULT_CODE,
                resultLabel = AccountingObjectComposeFragment.ACCOUNTING_OBJECT_RESULT,
                resultAction = {
                    (it as AccountingObjectResult?)?.accountingObject?.let {
                        accept(
                            DocumentCreateStore.Intent.OnAccountingObjectSelected(it)
                        )
                    }
                }
            ),
        )

    override fun renderState(
        state: DocumentCreateStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            DocumentCreateScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(DocumentCreateStore.Intent.OnBackClicked)
                },
                onDropClickListener = {
                    accept(DocumentCreateStore.Intent.OnDropClicked)
                },
                onParamClickListener = {
                    accept(DocumentCreateStore.Intent.OnParamClicked(it))
                },
                onPageChanged = {
                    accept(DocumentCreateStore.Intent.OnSelectPage(it))
                },
                onParamCrossClickListener = {
                    accept(DocumentCreateStore.Intent.OnParamCrossClicked(it))
                },
                onSaveClickListener = {
                    accept(DocumentCreateStore.Intent.OnSaveClicked)
                },
                onChooseClickListener = {
                    accept(DocumentCreateStore.Intent.OnChooseClicked)
                },
                onSettingsClickListener = {
                    accept(DocumentCreateStore.Intent.OnSettingsClicked)
                },
                onPrevClickListener = {
                    accept(DocumentCreateStore.Intent.OnPrevClicked)
                },
                onNextClickListener = {
                    accept(DocumentCreateStore.Intent.OnNextClicked)
                }
            )
        }
    }
}
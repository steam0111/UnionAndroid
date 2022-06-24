package com.itrocket.union.nomenclatureDetail.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.nomenclatureDetail.NomenclatureDetailModule
import com.itrocket.union.nomenclatureDetail.presentation.store.NomenclatureDetailStore

class NomenclatureDetailComposeFragment :
    BaseComposeFragment<NomenclatureDetailStore.Intent, NomenclatureDetailStore.State, NomenclatureDetailStore.Label>(
        NomenclatureDetailModule.NOMENCLATURE_DETAIL_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<NomenclatureDetailComposeFragmentArgs>()

    override fun renderState(
        state: NomenclatureDetailStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            NomenclatureDetailScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(NomenclatureDetailStore.Intent.OnBackClicked)
                }
            )
        }
    }
}
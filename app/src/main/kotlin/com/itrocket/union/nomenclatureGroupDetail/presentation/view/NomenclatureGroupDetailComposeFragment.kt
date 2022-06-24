package com.itrocket.union.nomenclatureGroupDetail.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.nomenclatureGroupDetail.NomenclatureGroupDetailModule
import com.itrocket.union.nomenclatureGroupDetail.presentation.store.NomenclatureGroupDetailStore

class NomenclatureGroupDetailComposeFragment :
    BaseComposeFragment<NomenclatureGroupDetailStore.Intent, NomenclatureGroupDetailStore.State, NomenclatureGroupDetailStore.Label>(
        NomenclatureGroupDetailModule.NOMENCLATURE_GROUP_DETAIL_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<NomenclatureGroupDetailComposeFragmentArgs>()

    override fun renderState(
        state: NomenclatureGroupDetailStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            NomenclatureGroupDetailScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(NomenclatureGroupDetailStore.Intent.OnBackClicked)
                }
            )
        }
    }
}
package com.itrocket.union.nomenclatureGroup.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.nomenclatureGroup.NomenclatureGroupModule.NOMENCLATUREGROUP_VIEW_MODEL_QUALIFIER
import com.itrocket.union.nomenclatureGroup.presentation.store.NomenclatureGroupStore

class NomenclatureGroupComposeFragment :
    BaseComposeFragment<NomenclatureGroupStore.Intent, NomenclatureGroupStore.State, NomenclatureGroupStore.Label>(
        NOMENCLATUREGROUP_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<NomenclatureGroupComposeFragmentArgs>()

    override fun renderState(
        state: NomenclatureGroupStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            NomenclatureGroupScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(NomenclatureGroupStore.Intent.OnBackClicked)
                },
                onItemClick = {
                    accept(NomenclatureGroupStore.Intent.OnItemClick(it))
                }
            )
        }
    }
}
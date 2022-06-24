package com.itrocket.union.nomenclature.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.nomenclature.NomenclatureModule.NOMENCLATURE_VIEW_MODEL_QUALIFIER
import com.itrocket.union.nomenclature.presentation.store.NomenclatureStore

class NomenclatureComposeFragment :
    BaseComposeFragment<NomenclatureStore.Intent, NomenclatureStore.State, NomenclatureStore.Label>(
        NOMENCLATURE_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<NomenclatureComposeFragmentArgs>()

    override fun renderState(
        state: NomenclatureStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            NomenclatureScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(NomenclatureStore.Intent.OnBackClicked)
                },
                onItemClickListener = {
                    accept(NomenclatureStore.Intent.OnItemClicked(it.id))
                }
            )
        }
    }
}
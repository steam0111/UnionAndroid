package com.itrocket.union.nomenclature.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.inventories.presentation.store.InventoriesStore
import com.itrocket.core.navigation.FragmentResult
import com.itrocket.union.filter.presentation.view.FilterComposeFragment
import com.itrocket.union.nomenclature.NomenclatureModule.NOMENCLATURE_VIEW_MODEL_QUALIFIER
import com.itrocket.union.nomenclature.presentation.store.NomenclatureStore
import com.itrocket.union.selectParams.presentation.store.SelectParamsResult

class NomenclatureComposeFragment :
    BaseComposeFragment<NomenclatureStore.Intent, NomenclatureStore.State, NomenclatureStore.Label>(
        NOMENCLATURE_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<NomenclatureComposeFragmentArgs>()

    override val fragmentResultList: List<FragmentResult>
        get() = listOf(
            FragmentResult(
                resultCode = FilterComposeFragment.FILTER_RESULT_CODE,
                resultLabel = FilterComposeFragment.FILTER_RESULT_LABEL,
                resultAction = {
                    (it as SelectParamsResult?)?.params?.let {
                        accept(NomenclatureStore.Intent.OnFilterResult(it))
                    }
                }
            )
        )

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
                },
                onSearchTextChanged = {
                    accept(NomenclatureStore.Intent.OnSearchTextChanged(it))
                },
                onSearchClickListener = {
                    accept(NomenclatureStore.Intent.OnSearchClicked)
                },
                onFilterClickListener = { accept(NomenclatureStore.Intent.OnFilterClicked) }
            )
        }
    }
}
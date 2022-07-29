package com.itrocket.union.nomenclature.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.filter.domain.entity.CatalogType
import com.itrocket.union.filter.presentation.store.FilterArguments
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.nomenclature.domain.entity.NomenclatureDomain
import com.itrocket.union.nomenclature.presentation.view.NomenclatureComposeFragmentDirections
import com.itrocket.union.nomenclatureDetail.presentation.store.NomenclatureDetailArguments

interface NomenclatureStore :
    Store<NomenclatureStore.Intent, NomenclatureStore.State, NomenclatureStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
        class OnItemClicked(val id: String) : Intent()
        data class OnSearchTextChanged(val searchText: String) : Intent()
        object OnSearchClicked : Intent()
        object OnFilterClicked : Intent()
        class OnFilterResult(val params: List<ParamDomain>) : Intent()
    }

    data class State(
        val nomenclatures: List<NomenclatureDomain> = emptyList(),
        val isLoading: Boolean = false,
        val isShowSearch: Boolean = false,
        val searchText: String = ""
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel

        data class ShowDetail(val id: String) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = NomenclatureComposeFragmentDirections.toNomenclatureDetail(
                    NomenclatureDetailArguments(id = id)
                )
        }

        data class ShowFilter(val filters: List<ParamDomain>) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = NomenclatureComposeFragmentDirections.toFilter(
                    FilterArguments(
                        filters,
                        CatalogType.Nomenclatures
                    )
                )
        }
    }
}
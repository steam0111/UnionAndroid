package com.itrocket.union.nomenclatureGroup.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.nomenclatureGroup.domain.entity.NomenclatureGroupDomain
import com.itrocket.union.nomenclatureGroup.presentation.view.NomenclatureGroupComposeFragmentDirections
import com.itrocket.union.nomenclatureGroupDetail.presentation.store.NomenclatureGroupDetailArguments

interface NomenclatureGroupStore :
    Store<NomenclatureGroupStore.Intent, NomenclatureGroupStore.State, NomenclatureGroupStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
        class OnItemClick(val id: String) : Intent()
        data class OnSearchTextChanged(val searchText: String) : Intent()
        object OnSearchClicked : Intent()
    }

    data class State(
        val nomenclatureGroups: List<NomenclatureGroupDomain> = emptyList(),
        val isLoading: Boolean = false,
        val isShowSearch: Boolean = false,
        val searchText: String = ""
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
        data class ShowDetail(val id: String) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = NomenclatureGroupComposeFragmentDirections.toNomenclatureGroupDetail(
                    NomenclatureGroupDetailArguments(id = id)
                )
        }
    }
}
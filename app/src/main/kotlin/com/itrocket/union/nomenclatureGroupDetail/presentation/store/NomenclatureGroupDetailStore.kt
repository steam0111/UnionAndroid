package com.itrocket.union.nomenclatureGroupDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.nomenclatureGroupDetail.domain.entity.NomenclatureGroupDetailDomain


interface NomenclatureGroupDetailStore :
    Store<NomenclatureGroupDetailStore.Intent, NomenclatureGroupDetailStore.State, NomenclatureGroupDetailStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
    }

    data class State(
        val item: NomenclatureGroupDetailDomain,
        val isLoading: Boolean = false
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(),
            DefaultNavigationErrorLabel
    }
}
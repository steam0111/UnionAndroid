package com.itrocket.union.nomenclatureDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.nomenclatureDetail.domain.entity.NomenclatureDetailDomain

interface NomenclatureDetailStore :
    Store<NomenclatureDetailStore.Intent, NomenclatureDetailStore.State, NomenclatureDetailStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
    }

    data class State(
        val item: NomenclatureDetailDomain,
        val isLoading: Boolean = false
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(),
            DefaultNavigationErrorLabel
    }
}
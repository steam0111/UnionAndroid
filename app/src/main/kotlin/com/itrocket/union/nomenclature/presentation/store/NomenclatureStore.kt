package com.itrocket.union.nomenclature.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.nomenclature.domain.entity.NomenclatureDomain

interface NomenclatureStore : Store<NomenclatureStore.Intent, NomenclatureStore.State, NomenclatureStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
    }

    data class State(
        val nomenclatures: List<NomenclatureDomain> = emptyList(),
        val isLoading: Boolean = false
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}
package com.itrocket.union.nomenclatureGroup.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.nomenclatureGroup.domain.entity.NomenclatureGroupDomain

interface NomenclatureGroupStore : Store<NomenclatureGroupStore.Intent, NomenclatureGroupStore.State, NomenclatureGroupStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
    }

    data class State(
        val nomenclatureGroups: List<NomenclatureGroupDomain> = emptyList(),
        val isLoading: Boolean = false
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}
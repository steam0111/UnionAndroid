package com.itrocket.union.labelTypeDetail.presentation.store

import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.union.labelTypeDetail.domain.entity.LabelTypeDetailDomain

interface LabelTypeDetailStore :
    Store<LabelTypeDetailStore.Intent, LabelTypeDetailStore.State, LabelTypeDetailStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
    }

    data class State(
        val item: LabelTypeDetailDomain = LabelTypeDetailDomain(listOf()),
        val isLoading: Boolean = false
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}
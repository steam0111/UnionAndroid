package com.itrocket.union.branchDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.branchDetail.domain.entity.BranchDetailDomain

interface BranchDetailStore :
    Store<BranchDetailStore.Intent, BranchDetailStore.State, BranchDetailStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
    }

    data class State(
        val item: BranchDetailDomain = BranchDetailDomain(),
        val isLoading: Boolean = false
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(),
            DefaultNavigationErrorLabel
    }
}
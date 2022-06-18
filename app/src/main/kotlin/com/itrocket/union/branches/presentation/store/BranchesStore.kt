package com.itrocket.union.branches.presentation.store

import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.union.branches.domain.entity.BranchesDomain

interface BranchesStore : Store<BranchesStore.Intent, BranchesStore.State, BranchesStore.Label> {

    sealed class Intent {
        object OnSearchClicked : Intent()
        object OnFilterClicked : Intent()
        object OnBackClicked : Intent()
        data class OnBranchClicked(val branch: BranchesDomain) : Intent()
    }

    data class State(
        val branches: List<BranchesDomain> = listOf(),
        val isLoading: Boolean = false
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}
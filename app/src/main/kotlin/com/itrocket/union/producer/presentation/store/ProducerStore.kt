package com.itrocket.union.producer.presentation.store

import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.union.producer.domain.entity.ProducerDomain

interface ProducerStore : Store<ProducerStore.Intent, ProducerStore.State, ProducerStore.Label> {

    sealed class Intent {
        object OnSearchClicked : Intent()
        object OnFilterClicked : Intent()
        object OnBackClicked : Intent()
        data class OnProducerClicked(val producer: ProducerDomain) : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val producers: List<ProducerDomain> = listOf()
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}
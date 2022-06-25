package com.itrocket.union.producerDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.producerDetail.domain.entity.ProducerDetailDomain


interface ProducerDetailStore :
    Store<ProducerDetailStore.Intent, ProducerDetailStore.State, ProducerDetailStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
    }

    data class State(
        val item: ProducerDetailDomain,
        val isLoading: Boolean = false
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(),
            DefaultNavigationErrorLabel
    }
}
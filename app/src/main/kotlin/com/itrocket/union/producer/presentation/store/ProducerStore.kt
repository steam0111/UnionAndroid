package com.itrocket.union.producer.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.producer.domain.entity.ProducerDomain
import com.itrocket.union.producer.presentation.view.ProducerComposeFragmentDirections
import com.itrocket.union.producerDetail.presentation.store.ProducerDetailArguments

interface ProducerStore : Store<ProducerStore.Intent, ProducerStore.State, ProducerStore.Label> {

    sealed class Intent {
        object OnSearchClicked : Intent()
        object OnFilterClicked : Intent()
        object OnBackClicked : Intent()
        data class OnProducerClicked(val id: String) : Intent()
        data class OnSearchTextChanged(val searchText: String) : Intent()
        object OnLoadNext : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val producers: List<ProducerDomain> = listOf(),
        val isShowSearch: Boolean = false,
        val searchText: String = "",
        val isListEndReached: Boolean = false
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
        data class ShowDetail(val id: String) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = ProducerComposeFragmentDirections.toProducerDetail(
                    ProducerDetailArguments(id = id)
                )
        }
    }
}
package com.itrocket.union.labelType.presentation.store

import androidx.navigation.NavDirections
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.union.labelType.domain.entity.LabelTypeDomain
import com.itrocket.union.labelType.presentation.view.LabelTypeComposeFragment
import com.itrocket.union.labelType.presentation.view.LabelTypeComposeFragmentDirections
import com.itrocket.union.labelTypeDetail.presentation.store.LabelTypeDetailArguments

interface LabelTypeStore :
    Store<LabelTypeStore.Intent, LabelTypeStore.State, LabelTypeStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
        data class OnItemClicked(val id: String) : Intent()
        data class OnSearchTextChanged(val searchText: String) : Intent()
        object OnSearchClicked : Intent()
        object OnLoadNext : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val types: List<LabelTypeDomain> = listOf(),
        val isShowSearch: Boolean = false,
        val searchText: String = "",
        val isListEndReached: Boolean = false,
        val isSelectMode: Boolean
    )

    sealed class Label {
        data class GoBack(override val result: LabelTypeResult? = null) : Label(),
            GoBackNavigationLabel {

            override val resultCode: String
                get() = LabelTypeComposeFragment.LABEL_TYPE_RESULT_CODE

            override val resultLabel: String
                get() = LabelTypeComposeFragment.LABEL_TYPE_RESULT_LABEL
        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
        data class ShowDetail(val id: String) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = LabelTypeComposeFragmentDirections.toLabelTypeDetail(
                    LabelTypeDetailArguments(id = id)
                )
        }
    }
}
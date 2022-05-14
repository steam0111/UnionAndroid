package com.itrocket.union.authSelectUser.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.GoBackDialogNavigationLabel
import com.itrocket.union.authSelectUser.presentation.view.AuthSelectUserComposeFragment

interface AuthSelectUserStore :
    Store<AuthSelectUserStore.Intent, AuthSelectUserStore.State, AuthSelectUserStore.Label> {

    sealed class Intent {
        data class OnUserSearchTextChanged(val search: String) : Intent()
        data class OnUserSelected(val user: String) : Intent()
        object OnCrossClicked : Intent()
        object OnCancelClicked : Intent()
        object OnAcceptClicked : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val userList: List<String> = listOf(),
        val searchText: String = "",
        val selectedUser: String = ""
    )

    sealed class Label {
        data class GoBack(override val result: AuthSelectUserResult? = null) : Label(),
            GoBackDialogNavigationLabel {

            override val resultCode: String
                get() = AuthSelectUserComposeFragment.AUTH_SELECT_USER_RESULT_CODE

            override val resultLabel: String
                get() = AuthSelectUserComposeFragment.AUTH_SELECT_USER_RESULT
        }
    }
}
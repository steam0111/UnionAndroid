package com.itrocket.union.authSelectUser.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeBottomSheet
import com.itrocket.union.authSelectUser.AuthSelectUserModule.AUTHSELECTUSER_VIEW_MODEL_QUALIFIER
import com.itrocket.union.authSelectUser.presentation.store.AuthSelectUserStore

class AuthSelectUserComposeFragment :
    BaseComposeBottomSheet<AuthSelectUserStore.Intent, AuthSelectUserStore.State, AuthSelectUserStore.Label>(
        AUTHSELECTUSER_VIEW_MODEL_QUALIFIER
    ) {

    override fun renderState(
        state: AuthSelectUserStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            AuthSelectUserScreen(
                state = state,
                appInsets = appInsets,
                onUserSearchTextChanged = {
                    accept(AuthSelectUserStore.Intent.OnUserSearchTextChanged(it))
                },
                onUserSelected = {
                    accept(AuthSelectUserStore.Intent.OnUserSelected(it))
                },
                onCrossClickListener = {
                    accept(AuthSelectUserStore.Intent.OnCrossClicked)
                },
                onCancelClickListener = {
                    accept(AuthSelectUserStore.Intent.OnCancelClicked)
                },
                onAcceptClickListener = {
                    accept(AuthSelectUserStore.Intent.OnAcceptClicked)
                }
            )
        }
    }

    companion object {
        const val AUTH_SELECT_USER_RESULT_CODE = "auth select user result code"
        const val AUTH_SELECT_USER_RESULT = "auth select user result"
    }
}
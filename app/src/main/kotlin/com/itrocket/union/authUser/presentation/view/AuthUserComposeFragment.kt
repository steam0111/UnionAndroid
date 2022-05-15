package com.itrocket.union.authUser.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.authUser.AuthUserModule.AUTHUSER_VIEW_MODEL_QUALIFIER
import com.itrocket.union.authUser.presentation.store.AuthUserStore

class AuthUserComposeFragment :
    BaseComposeFragment<AuthUserStore.Intent, AuthUserStore.State, AuthUserStore.Label>(
        AUTHUSER_VIEW_MODEL_QUALIFIER
    ) {

    override fun renderState(
        state: AuthUserStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            AuthUserScreen(
                state = state,
                appInsets = appInsets,
                onLoginChanged = {
                    accept(AuthUserStore.Intent.OnLoginChanged(it))
                },
                onPasswordChanged = {
                    accept(AuthUserStore.Intent.OnPasswordChanged(it))
                }
            )
        }
    }
}
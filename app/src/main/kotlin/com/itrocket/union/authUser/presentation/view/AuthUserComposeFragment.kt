package com.itrocket.union.authUser.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.authContainer.presentation.view.ButtonEnableHandler
import com.itrocket.union.authContainer.presentation.view.NextClickHandler
import com.itrocket.union.authContainer.presentation.view.NextFinishHandler
import com.itrocket.union.authUser.AuthUserModule.AUTHUSER_VIEW_MODEL_QUALIFIER
import com.itrocket.union.authUser.presentation.store.AuthUserStore

class AuthUserComposeFragment :
    BaseComposeFragment<AuthUserStore.Intent, AuthUserStore.State, AuthUserStore.Label>(
        AUTHUSER_VIEW_MODEL_QUALIFIER
    ), NextClickHandler {

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
                },
                onPasswordVisibilityClickListener = {
                    accept(AuthUserStore.Intent.OnPasswordVisibilityClicked)
                }
            )
        }
    }

    override fun handleLabel(label: AuthUserStore.Label) {
        super.handleLabel(label)
        when (label) {
            is AuthUserStore.Label.ChangeEnable -> (parentFragment as? ButtonEnableHandler)?.isButtonEnable(
                label.enabled
            )
        }
    }

    override fun onNext() {
        accept(AuthUserStore.Intent.OnNextClicked)
    }

}
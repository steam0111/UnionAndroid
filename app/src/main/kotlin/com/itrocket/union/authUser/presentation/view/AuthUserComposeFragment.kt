package com.itrocket.union.authUser.presentation.view

import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.authContainer.presentation.view.AuthContainer
import com.itrocket.union.authContainer.presentation.view.NextClickHandler
import com.itrocket.union.authUser.AuthUserModule.AUTHUSER_VIEW_MODEL_QUALIFIER
import com.itrocket.union.authUser.presentation.store.AuthUserStore
import com.itrocket.union.utils.fragment.ChildBackPressedHandler

class AuthUserComposeFragment :
    BaseComposeFragment<AuthUserStore.Intent, AuthUserStore.State, AuthUserStore.Label>(
        AUTHUSER_VIEW_MODEL_QUALIFIER
    ), NextClickHandler {

    override val onBackPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (parentFragment as? ChildBackPressedHandler)?.onChildBackPressed()
            }
        }
    }

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
                },
                onActiveDirectoryChanged = {
                    accept(AuthUserStore.Intent.OnActiveDirectoryChanged)
                }
            )
        }
    }

    override fun handleLabel(label: AuthUserStore.Label) {
        super.handleLabel(label)
        when (label) {
            is AuthUserStore.Label.ChangeEnable -> (parentFragment as? AuthContainer)?.isButtonEnable(
                label.enabled
            )
            is AuthUserStore.Label.ParentLoading -> (parentFragment as? AuthContainer)?.isLoading(
                label.isLoading
            )
            else -> {}
        }
    }

    override fun onNext() {
        accept(AuthUserStore.Intent.OnNextClicked)
    }
}
package com.itrocket.union.authMain.presentation.view

import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import com.itrocket.union.authMain.AuthMainModule.AUTHMAIN_VIEW_MODEL_QUALIFIER
import com.itrocket.union.authMain.presentation.store.AuthMainStore
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.base.AppInsets
import androidx.navigation.fragment.navArgs

class AuthMainComposeFragment :
    BaseComposeFragment<AuthMainStore.Intent, AuthMainStore.State, AuthMainStore.Label>(
        AUTHMAIN_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<AuthMainComposeFragmentArgs>()

    override fun renderState(
        state: AuthMainStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            AuthMainScreen(
                state = state,
                appInsets = appInsets,
                onPasswordChanged = {
                    accept(AuthMainStore.Intent.OnPasswordChanged(it))
                },
                onDatabaseSettingsClickListener = {
                    accept(AuthMainStore.Intent.OnDatabaseSettingsClicked)
                },
                onSignInClickListener = {
                    accept(AuthMainStore.Intent.OnSignInClicked)
                },
                onUserChangeClickListener = {
                    accept(AuthMainStore.Intent.OnUserChangeClicked)
                },
                onPasswordVisibilityClickListener = {
                    accept(AuthMainStore.Intent.OnPasswordVisibilityClicked)
                }
            )
        }
    }

    override fun handleLabel(label: AuthMainStore.Label) {
        super.handleLabel(label)
        when (label) {
            is AuthMainStore.Label.Error -> showError(label.message)
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}
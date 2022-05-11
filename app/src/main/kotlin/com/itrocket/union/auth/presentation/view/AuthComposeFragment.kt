package com.itrocket.union.auth.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import com.itrocket.union.auth.AuthModule.AUTH_VIEW_MODEL_QUALIFIER
import com.itrocket.union.auth.presentation.store.AuthStore
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.base.AppInsets
import com.itrocket.union.R

class AuthComposeFragment : BaseComposeFragment<AuthStore.Intent, AuthStore.State, AuthStore.Label>(
    AUTH_VIEW_MODEL_QUALIFIER
) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_auth_host, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun renderState(
        state: AuthStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            AuthScreen(
                state = state,
                appInsets = appInsets,
                onPrevClickListener = {
                    accept(AuthStore.Intent.OnPrevClicked)
                },
                onNextClickListener = {
                    accept(AuthStore.Intent.OnNextClicked)
                }
            )
        }
    }
}
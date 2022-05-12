package com.itrocket.union.authContainer.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updatePadding
import androidx.fragment.app.commitNow
import com.itrocket.union.authContainer.AuthContainerModule.AUTH_VIEW_MODEL_QUALIFIER
import com.itrocket.union.authContainer.presentation.store.AuthContainerStore
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.base.AppInsets
import com.itrocket.union.R
import com.itrocket.union.serverConnect.presentation.view.ServerConnectComposeFragment
import com.itrocket.utils.toPx

class AuthContainerComposeFragment :
    BaseComposeFragment<AuthContainerStore.Intent, AuthContainerStore.State, AuthContainerStore.Label>(
        AUTH_VIEW_MODEL_QUALIFIER
    ) {

    private val authContainer by lazy {
        view?.findViewById<ConstraintLayout>(R.id.authContainer)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_auth_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val composeView = view.findViewById<ComposeView>(R.id.composeView)
        childFragmentManager.commitNow {
            add(R.id.navigationContainer, ServerConnectComposeFragment())
        }
        super.onViewCreated(composeView, savedInstanceState)
    }

    override fun renderState(
        state: AuthContainerStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        authContainer?.updatePadding(
            top = appInsets.topInset.toPx,
            bottom = appInsets.bottomInset.toPx
        )
        composeView.setContent {
            AuthScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(AuthContainerStore.Intent.OnBackClicked)
                },
                onNextClickListener = {
                    accept(AuthContainerStore.Intent.OnNextClicked)
                }
            )
        }
    }
}
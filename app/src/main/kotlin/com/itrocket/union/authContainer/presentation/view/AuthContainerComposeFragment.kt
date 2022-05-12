package com.itrocket.union.authContainer.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updatePadding
import com.itrocket.union.authContainer.AuthContainerModule.AUTH_VIEW_MODEL_QUALIFIER
import com.itrocket.union.authContainer.presentation.store.AuthContainerStore
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.base.AppInsets
import com.itrocket.union.R
import com.itrocket.utils.toPx

class AuthContainerComposeFragment : BaseComposeFragment<AuthContainerStore.Intent, AuthContainerStore.State, AuthContainerStore.Label>(
    AUTH_VIEW_MODEL_QUALIFIER
) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_auth_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val composeView = view.findViewById<ComposeView>(R.id.composeView)
        super.onViewCreated(composeView, savedInstanceState)
    }

    override fun renderState(
        state: AuthContainerStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        val container = view?.findViewById<ConstraintLayout>(R.id.clAuth)
        container?.updatePadding(top = appInsets.topInset.toPx, bottom = appInsets.bottomInset.toPx)
        composeView.setContent {
            AuthScreen(
                state = state,
                appInsets = appInsets,
                onPrevClickListener = {
                    accept(AuthContainerStore.Intent.OnPrevClicked)
                },
                onNextClickListener = {
                    accept(AuthContainerStore.Intent.OnNextClicked)
                }
            )
        }
    }
}
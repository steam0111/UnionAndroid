package com.itrocket.union.auth.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updatePadding
import androidx.fragment.app.commitNow
import com.itrocket.union.auth.AuthModule.AUTH_VIEW_MODEL_QUALIFIER
import com.itrocket.union.auth.presentation.store.AuthStore
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.base.AppInsets
import com.itrocket.union.R
import com.itrocket.union.authAndLicense.presentation.view.AuthAndLicenseComposeFragment
import com.itrocket.utils.toDp
import com.itrocket.utils.toPx

class AuthComposeFragment : BaseComposeFragment<AuthStore.Intent, AuthStore.State, AuthStore.Label>(
    AUTH_VIEW_MODEL_QUALIFIER
) {

    private val container: ConstraintLayout? by lazy {
        view?.findViewById(R.id.clAuth)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_auth_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val composeView = view.findViewById<ComposeView>(R.id.composeView)
        childFragmentManager.commitNow {
            add(R.id.container, AuthAndLicenseComposeFragment())
        }
        super.onViewCreated(composeView, savedInstanceState)
    }

    override fun renderState(
        state: AuthStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        container?.updatePadding(top = appInsets.topInset.toPx, bottom = appInsets.bottomInset.toPx)
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
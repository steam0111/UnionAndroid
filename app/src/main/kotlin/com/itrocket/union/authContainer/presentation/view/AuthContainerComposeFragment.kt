package com.itrocket.union.authContainer.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.R
import com.itrocket.union.addFragment
import com.itrocket.union.authContainer.AuthContainerModule.AUTH_VIEW_MODEL_QUALIFIER
import com.itrocket.union.authContainer.domain.entity.AuthContainerStep
import com.itrocket.union.authContainer.presentation.store.AuthContainerStore
import com.itrocket.union.authUser.presentation.view.AuthUserComposeFragment
import com.itrocket.union.replaceFragment
import com.itrocket.union.serverConnect.presentation.view.ServerConnectComposeFragment
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseToolbar
import com.itrocket.union.ui.white
import com.itrocket.union.utils.fragment.ChildBackPressedHandler
import com.itrocket.utils.toPx

class AuthContainerComposeFragment :
    BaseComposeFragment<AuthContainerStore.Intent, AuthContainerStore.State, AuthContainerStore.Label>(
        AUTH_VIEW_MODEL_QUALIFIER
    ), AuthContainer, ChildBackPressedHandler {

    override val navArgs by navArgs<AuthContainerComposeFragmentArgs>()

    private var authContainer: ConstraintLayout? = null
    private var toolBarComposeView: ComposeView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments?.containsKey("AuthContainerArguments") != true) {
            arguments = bundleOf(
                "AuthContainerArguments" to AuthContainerArguments(isShowBackButton = false)
            )
        }

        if (savedInstanceState == null) {
            initStartFragment()
        }
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
        authContainer = view.findViewById(R.id.authContainer)
        toolBarComposeView = view.findViewById(R.id.toolBarComposeView)
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
        toolBarComposeView?.setContent {
            if (state.isShowBackButton) {
                Column {
                    Spacer(modifier = Modifier.height(appInsets.topInset.dp))
                    BaseToolbar(
                        title = stringResource(id = R.string.auth_title),
                        startImageId = R.drawable.ic_cross,
                        onStartImageClickListener = {
                            accept(AuthContainerStore.Intent.OnBackClicked)
                        }
                    )
                }
            }
        }
    }

    override fun handleLabel(label: AuthContainerStore.Label) {
        super.handleLabel(label)
        when (label) {
            AuthContainerStore.Label.HandleNext -> {
                val nextClickHandler =
                    childFragmentManager.fragments.lastOrNull() as? NextClickHandler
                nextClickHandler?.onNext()
            }
            is AuthContainerStore.Label.NavigateNext -> {
                navigateNext(label.step)
            }
            AuthContainerStore.Label.NavigateBack -> {
                childFragmentManager.popBackStack()
            }
        }
    }

    override fun onNextFinished() {
        accept(AuthContainerStore.Intent.OnNextFinished)
    }

    override fun isLoading(isLoading: Boolean) {
        accept(AuthContainerStore.Intent.OnLoadingChanged(isLoading))
    }

    private fun navigateNext(currentStep: AuthContainerStep) {
        replaceFragment(getFragmentByStep(currentStep))
    }

    private fun getFragmentByStep(step: AuthContainerStep): Fragment {
        return when (step) {
            AuthContainerStep.CONNECT_TO_SERVER -> ServerConnectComposeFragment()
            AuthContainerStep.AUTH_USER -> AuthUserComposeFragment()
        }
    }

    private fun initStartFragment() {
        childFragmentManager.addFragment(
            R.id.navigationContainer,
            ServerConnectComposeFragment(),
        )
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.replaceFragment(R.id.navigationContainer, fragment)
    }

    override fun isButtonEnable(enabled: Boolean) {
        accept(AuthContainerStore.Intent.OnEnableChanged(enabled))
    }

    override fun onChildBackPressed() {
        accept(AuthContainerStore.Intent.OnBackClicked)
    }
}
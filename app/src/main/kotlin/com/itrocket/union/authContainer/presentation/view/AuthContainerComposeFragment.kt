package com.itrocket.union.authContainer.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import com.itrocket.union.authContainer.AuthContainerModule.AUTH_VIEW_MODEL_QUALIFIER
import com.itrocket.union.authContainer.presentation.store.AuthContainerStore
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.base.AppInsets
import com.itrocket.union.R
import com.itrocket.union.authContainer.domain.entity.AuthContainerStep
import com.itrocket.union.serverConnect.presentation.view.ServerConnectComposeFragment
import com.itrocket.union.authUser.presentation.view.AuthUserComposeFragment
import com.itrocket.utils.toPx

class AuthContainerComposeFragment :
    BaseComposeFragment<AuthContainerStore.Intent, AuthContainerStore.State, AuthContainerStore.Label>(
        AUTH_VIEW_MODEL_QUALIFIER
    ), NextFinishHandler {

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
        initStartFragment()
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

    override fun handleLabel(label: AuthContainerStore.Label) {
        when (label) {
            AuthContainerStore.Label.HandleNext -> {
                val nextClickHandler =
                    childFragmentManager.fragments.lastOrNull() as? NextClickHandler
                nextClickHandler?.onNext()
            }
            is AuthContainerStore.Label.NavigateNext -> {
                navigateNext(label.currentStep)
            }
            is AuthContainerStore.Label.NavigateBack -> if (label.currentStep.ordinal > 0) {
                childFragmentManager.popBackStackImmediate()
            }
        }
    }

    override fun onNextFinished() {
        accept(AuthContainerStore.Intent.OnNextFinished)
    }

    private fun navigateNext(currentStep: AuthContainerStep) {
        when (currentStep) {
            AuthContainerStep.CONNECT_TO_SERVER -> {
                replaceFragment(AuthUserComposeFragment())
            }
            AuthContainerStep.AUTH_USER -> {
                //Add navigate to Hi, {username} screen
            }
        }
    }

    private fun initStartFragment() {
        childFragmentManager.beginTransaction()
            .add(
                R.id.navigationContainer,
                ServerConnectComposeFragment(),
                ServerConnectComposeFragment::class.java.name
            )
            .addToBackStack(ServerConnectComposeFragment::class.java.name)
            .commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(
                R.id.navigationContainer,
                fragment,
                fragment::class.java.name
            )
            .addToBackStack(fragment::class.java.name)
            .commit()
    }
}
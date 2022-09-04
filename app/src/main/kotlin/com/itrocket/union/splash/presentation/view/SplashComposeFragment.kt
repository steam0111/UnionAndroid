package com.itrocket.union.splash.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.union.splash.SplashModule.SPLASH_VIEW_MODEL_QUALIFIER
import com.itrocket.union.splash.presentation.store.SplashStore
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.base.AppInsets

class SplashComposeFragment :
    BaseComposeFragment<SplashStore.Intent, SplashStore.State, SplashStore.Label>(
        SPLASH_VIEW_MODEL_QUALIFIER
    ) {
    override fun renderState(
        state: SplashStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            SplashScreen(
                state = state,
                appInsets = appInsets,
            )
        }
    }
}
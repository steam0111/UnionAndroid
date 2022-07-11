package com.itrocket.union.location.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.location.LocationModule.LOCATION_VIEW_MODEL_QUALIFIER
import com.itrocket.union.location.presentation.store.LocationStore

class LocationComposeFragment :
    BaseComposeFragment<LocationStore.Intent, LocationStore.State, LocationStore.Label>(
        LOCATION_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<LocationComposeFragmentArgs>()

    override fun renderState(
        state: LocationStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            LocationScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(LocationStore.Intent.OnBackClicked)
                },
                onFinishClickListener = {
                    accept(LocationStore.Intent.OnFinishClicked)
                },
                onAcceptClickListener = {
                    accept(LocationStore.Intent.OnAcceptClicked)
                },
                onCrossClickListener = {
                    accept(LocationStore.Intent.OnCrossClicked)
                },
                onPlaceSelected = {
                    accept(LocationStore.Intent.OnPlaceSelected(it))
                },
                onSearchTextChanged = {
                    accept(LocationStore.Intent.OnSearchTextChanged(it))
                }
            )
        }
    }

    companion object {
        const val LOCATION_RESULT_CODE = "location result code"
        const val LOCATION_RESULT = "location result"
    }
}
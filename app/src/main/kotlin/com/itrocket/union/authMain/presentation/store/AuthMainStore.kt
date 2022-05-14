package com.itrocket.union.authMain.presentation.store

import android.os.Bundle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.ShowBottomSheetNavigationLabel
import com.itrocket.union.R
import com.itrocket.union.authMain.presentation.view.AuthMainComposeFragmentDirections
import com.itrocket.union.authSelectUser.presentation.store.AuthSelectUserResult
import com.itrocket.union.authSelectUser.presentation.view.AuthSelectUserComposeFragment

interface AuthMainStore : Store<AuthMainStore.Intent, AuthMainStore.State, AuthMainStore.Label> {

    sealed class Intent {
        data class OnUserChanged(val result: AuthSelectUserResult) : Intent()
        data class OnPasswordChanged(val password: String) : Intent()
        data class OnPasswordVisibilityClicked(val passwordVisualTransformation: VisualTransformation) :
            Intent()

        object OnSignInClicked : Intent()
        object OnUserChangeClicked : Intent()
        object OnDatabaseSettingsClicked : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val login: String,
        val password: String,
        val passwordVisualTransformation: VisualTransformation = PasswordVisualTransformation()
    )

    sealed class Label {
        object ShowDocumentMenu : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = AuthMainComposeFragmentDirections.toDocumentsMenu()
        }

        object ShowSelectUser : Label(), ShowBottomSheetNavigationLabel {
            override val arguments: Bundle
                get() = bundleOf()
            override val containerId: Int
                get() = R.id.mainActivityNavHostFragment
            override val fragment: Fragment
                get() = AuthSelectUserComposeFragment()

        }

    }
}
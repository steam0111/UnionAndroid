package com.itrocket.union.imageViewer.presentation.store

import android.net.Uri
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.image.domain.ImageDomain

interface ImageViewerStore :
    Store<ImageViewerStore.Intent, ImageViewerStore.State, ImageViewerStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
        data class OnImageSwipe(val page: Int) : Intent()
        object OnDeleteImageClicked : Intent()
        object OnAddImageClicked : Intent()
        data class OnImageTaken(val success: Boolean) : Intent()
        object OnMainClicked : Intent()
    }

    data class State(
        val page: Int,
        val imageUri: Uri? = null,
        val images: List<ImageDomain>,
        val isLoading: Boolean = false,
    )

    sealed class Label {
        data class ShowAddImage(val imageUri: Uri) : Label()
        object GoBack : Label(), GoBackNavigationLabel

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}
package com.itrocket.union.imageViewer.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.image.ImageDomain
import com.itrocket.union.imageViewer.domain.entity.ImageViewerResult
import com.itrocket.union.imageViewer.presentation.view.ImageViewerComposeFragment.Companion.IMAGE_VIEWER_RESULT_CODE
import com.itrocket.union.imageViewer.presentation.view.ImageViewerComposeFragment.Companion.IMAGE_VIEWER_RESULT_LABEL

interface ImageViewerStore :
    Store<ImageViewerStore.Intent, ImageViewerStore.State, ImageViewerStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
        data class OnImageSwipe(val page: Int) : Intent()
        object OnDeleteImageClicked : Intent()
        object OnAddImageClicked : Intent()
        object OnMainClicked : Intent()
    }

    data class State(
        val page: Int,
        val images: List<ImageDomain>,
    )

    sealed class Label {
        data class GoBack(override val result: ImageViewerResult?) : Label(),
            GoBackNavigationLabel {

            override val resultCode: String
                get() = IMAGE_VIEWER_RESULT_CODE

            override val resultLabel: String
                get() = IMAGE_VIEWER_RESULT_LABEL
        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}
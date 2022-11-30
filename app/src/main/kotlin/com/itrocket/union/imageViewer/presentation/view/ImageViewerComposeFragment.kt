package com.itrocket.union.imageViewer.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.imageViewer.ImageViewerModule.IMAGEVIEWER_VIEW_MODEL_QUALIFIER
import com.itrocket.union.imageViewer.presentation.store.ImageViewerStore

class ImageViewerComposeFragment :
    BaseComposeFragment<ImageViewerStore.Intent, ImageViewerStore.State, ImageViewerStore.Label>(
        IMAGEVIEWER_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<ImageViewerComposeFragmentArgs>()

    override fun renderState(
        state: ImageViewerStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            ImageViewerScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(ImageViewerStore.Intent.OnBackClicked)
                },
                onDeleteClickListener = {
                    accept(ImageViewerStore.Intent.OnDeleteImageClicked)
                },
                onImageSwipe = {
                    accept(ImageViewerStore.Intent.OnImageSwipe(it))
                },
                onAddPhotoClickListener = {
                    accept(ImageViewerStore.Intent.OnAddImageClicked)
                },
                onMainClickListener = {
                    accept(ImageViewerStore.Intent.OnMainClicked)
                }
            )
        }
    }

    companion object {
        const val IMAGE_VIEWER_RESULT_CODE = "IMAGE_VIEWER_RESULT_CODE"
        const val IMAGE_VIEWER_RESULT_LABEL = "IMAGE_VIEWER_RESULT_LABEL"
    }
}
package com.itrocket.union.imageViewer.presentation.view

import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
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

    private val takeImageActivityResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            accept(
                ImageViewerStore.Intent.OnImageTaken(success = success)
            )
        }

    override val onBackPressedCallback: OnBackPressedCallback
        get() = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                accept(ImageViewerStore.Intent.OnBackClicked)
            }
        }

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

    override fun handleLabel(label: ImageViewerStore.Label) {
        when (label) {
            is ImageViewerStore.Label.ShowAddImage -> {
                takeImageActivityResult.launch(label.imageUri)
            }
            else -> super.handleLabel(label)
        }
    }

    companion object {
        const val IMAGE_VIEWER_RESULT_CODE = "IMAGE_VIEWER_RESULT_CODE"
        const val IMAGE_VIEWER_RESULT_LABEL = "IMAGE_VIEWER_RESULT_LABEL"
    }
}
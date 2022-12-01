package com.itrocket.union.imageViewer.presentation.store

import android.net.Uri
import com.arkivanov.mvikotlin.core.store.*
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.R
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailStore
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailStoreFactory
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.image.domain.ImageDomain
import com.itrocket.union.image.domain.ImageInteractor
import com.itrocket.union.imageViewer.domain.ImageViewerInteractor
import com.itrocket.union.imageViewer.domain.entity.ImageViewerResult

class ImageViewerStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val imageViewerInteractor: ImageViewerInteractor,
    private val imageViewerArguments: ImageViewerArguments,
    private val imageInteractor: ImageInteractor,
    private val errorInteractor: ErrorInteractor
) {
    fun create(): ImageViewerStore =
        object : ImageViewerStore,
            Store<ImageViewerStore.Intent, ImageViewerStore.State, ImageViewerStore.Label> by storeFactory.create(
                name = "ImageViewerStore",
                initialState = ImageViewerStore.State(
                    images = imageViewerArguments.images,
                    page = imageViewerInteractor.getImagePage(
                        image = imageViewerArguments.currentImage,
                        images = imageViewerArguments.images
                    )
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<ImageViewerStore.Intent, Unit, ImageViewerStore.State, Result, ImageViewerStore.Label> =
        ImageViewerExecutor()

    private inner class ImageViewerExecutor :
        BaseExecutor<ImageViewerStore.Intent, Unit, ImageViewerStore.State, Result, ImageViewerStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> ImageViewerStore.State
        ) {
            dispatch(Result.Images(imageInteractor.getImagesFromImagesDomain(images = getState().images)))
        }

        override suspend fun executeIntent(
            intent: ImageViewerStore.Intent,
            getState: () -> ImageViewerStore.State
        ) {
            when (intent) {
                ImageViewerStore.Intent.OnBackClicked -> onBackClicked(getState = getState)
                ImageViewerStore.Intent.OnAddImageClicked -> onAddImageClicked()
                ImageViewerStore.Intent.OnDeleteImageClicked -> onDeleteImageClicked(getState = getState)
                is ImageViewerStore.Intent.OnImageSwipe -> onImageSwipe(page = intent.page)
                ImageViewerStore.Intent.OnMainClicked -> onMainClicked(getState = getState)
                is ImageViewerStore.Intent.OnImageTaken -> onImageTaken(
                    success = intent.success,
                    getState = getState
                )
            }
        }

        private fun onBackClicked(getState: () -> ImageViewerStore.State) {
            publish(ImageViewerStore.Label.GoBack(ImageViewerResult(getState().images)))
        }

        private suspend fun onImageTaken(
            success: Boolean,
            getState: () -> ImageViewerStore.State
        ) {
            val imageUri = getState().imageUri
            dispatch(Result.Loading(true))
            catchException {
                if (success && imageUri != null) {
                    val imageDomain = imageInteractor.saveImageFromContentUri(imageUri)
                    dispatch(
                        Result.Images(
                            images = imageInteractor.addImageDomain(
                                images = getState().images,
                                image = imageDomain
                            )
                        )
                    )
                } else {
                    throw errorInteractor.getThrowableByResId(R.string.image_taken_error)
                }
            }
            dispatch(Result.Loading(false))
        }

        private suspend fun onAddImageClicked() {
            catchException {
                val imageTmpUri = imageInteractor.getTmpFileUri()
                dispatch(Result.ImageUri(imageTmpUri))
                publish(ImageViewerStore.Label.ShowAddImage(imageTmpUri))
            }
        }

        private suspend fun onDeleteImageClicked(getState: () -> ImageViewerStore.State) {
            val imagesDelete = imageViewerInteractor.deleteImage(
                images = getState().images,
                page = getState().page
            )
            if (imagesDelete.images.isEmpty()) {
                publish(ImageViewerStore.Label.GoBack(ImageViewerResult(listOf())))
            } else {
                dispatch(Result.Page(page = imagesDelete.newPage))
                dispatch(Result.Images(images = imagesDelete.images))
            }
        }

        private fun onImageSwipe(page: Int) {
            dispatch(Result.Page(page))
        }

        private suspend fun onMainClicked(getState: () -> ImageViewerStore.State) {
            dispatch(
                Result.Images(
                    imageViewerInteractor.changeMainImage(
                        images = getState().images,
                        page = getState().page
                    )
                )
            )
        }

        override fun handleError(throwable: Throwable) {
            publish(ImageViewerStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }
    }

    private sealed class Result {
        data class Page(val page: Int) : Result()
        data class Images(val images: List<ImageDomain>) : Result()
        data class ImageUri(val imageUri: Uri) : Result()
        data class Loading(val isLoading: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<ImageViewerStore.State, Result> {
        override fun ImageViewerStore.State.reduce(result: Result) =
            when (result) {
                is Result.Images -> copy(images = result.images)
                is Result.Page -> copy(page = result.page)
                is Result.ImageUri -> copy(imageUri = result.imageUri)
                is Result.Loading -> copy(isLoading = result.isLoading)
            }
    }
}
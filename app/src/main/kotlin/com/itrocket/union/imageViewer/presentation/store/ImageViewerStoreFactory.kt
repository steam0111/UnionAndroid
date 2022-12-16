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
import kotlinx.coroutines.flow.catch

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
            listenImages(getState)
        }

        override suspend fun executeIntent(
            intent: ImageViewerStore.Intent,
            getState: () -> ImageViewerStore.State
        ) {
            when (intent) {
                ImageViewerStore.Intent.OnBackClicked -> onBackClicked()
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

        private fun onBackClicked() {
            publish(ImageViewerStore.Label.GoBack)
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
                    val accountingObjectId = imageViewerArguments.accountingObjectId
                    imageViewerInteractor.saveImage(
                        image = imageDomain,
                        accountingObjectId = accountingObjectId
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
            imageViewerInteractor.deleteImage(images = getState().images, page = getState().page)
        }

        private fun onImageSwipe(page: Int) {
            dispatch(Result.Page(page))
        }

        private suspend fun onMainClicked(getState: () -> ImageViewerStore.State) {
            imageViewerInteractor.changeMainImage(
                images = getState().images,
                page = getState().page
            )
        }

        private suspend fun listenImages(getState: () -> ImageViewerStore.State) {
            catchException {
                imageViewerInteractor.getImagesFlow(entityId = imageViewerArguments.accountingObjectId)
                    .catch {
                        handleError(it)
                    }.collect {
                        when {
                            it.isEmpty() -> publish(ImageViewerStore.Label.GoBack)
                            it.size <= getState().page -> {
                                dispatch(Result.Images(it))
                                dispatch(Result.Page(it.size - 1))
                            }
                            else -> dispatch(Result.Images(it))
                        }
                    }
            }
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

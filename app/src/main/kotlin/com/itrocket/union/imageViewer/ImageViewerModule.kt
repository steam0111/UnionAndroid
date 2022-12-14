package com.itrocket.union.imageViewer

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.core.parameter.parametersOf
import com.itrocket.union.imageViewer.domain.ImageViewerInteractor
import com.itrocket.union.imageViewer.presentation.store.ImageViewerStore
import com.itrocket.union.imageViewer.presentation.store.ImageViewerStoreFactory
import com.itrocket.union.imageViewer.presentation.view.ImageViewerComposeFragmentArgs
import com.itrocket.core.base.BaseViewModel

object ImageViewerModule {
    val IMAGEVIEWER_VIEW_MODEL_QUALIFIER = named("IMAGEVIEWER_VIEW_MODEL")

    val module = module {
        viewModel(IMAGEVIEWER_VIEW_MODEL_QUALIFIER) { (args: ImageViewerComposeFragmentArgs) ->
            BaseViewModel(get<ImageViewerStore>() {
                parametersOf(args)
            })
        }

        factory {
            ImageViewerInteractor(get(), get(), get())
        }

        factory { (args: ImageViewerComposeFragmentArgs) ->
            ImageViewerStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.imageViewerArguments,
                get(),
                get()
            ).create()
        }
    }
}
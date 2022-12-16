package com.itrocket.union.image

import com.itrocket.union.image.data.ImageRepositoryImpl
import com.itrocket.union.image.domain.ImageInteractor
import com.itrocket.union.image.domain.dependencies.ImageRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object ImageModule {

    val module = module {
        factory {
            ImageInteractor(repository = get())
        }
        single<ImageRepository> {
            ImageRepositoryImpl(applicationContext = androidContext(), coreDispatchers = get())
        }
    }
}
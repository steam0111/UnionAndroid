package com.itrocket.union.error

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object ErrorModule {
    val module = module {
        factory {
            ErrorInteractor(androidContext())
        }
    }
}
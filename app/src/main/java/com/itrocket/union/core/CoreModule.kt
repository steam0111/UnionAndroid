package com.itrocket.union.core

import com.itrocket.core.base.AppInsetsStateHolder
import org.koin.dsl.module

object CoreModule {

    val module = module {
        single<CoreDispatchers> {
            object : CoreDispatchers {}
        }
        single {
            AppInsetsStateHolder()
        }
    }
}
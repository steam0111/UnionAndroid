package com.itrocket.sgtin

import org.koin.dsl.module

object SgtinModule {

    val module = module {
        single<SgtinFormatter> {
            SgtinFormatterImpl()
        }
    }
}
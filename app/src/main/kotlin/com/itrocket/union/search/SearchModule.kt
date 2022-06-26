package com.itrocket.union.search

import org.koin.dsl.module

object SearchModule {
    val module = module {
        factory {
            SearchManager()
        }
    }
}
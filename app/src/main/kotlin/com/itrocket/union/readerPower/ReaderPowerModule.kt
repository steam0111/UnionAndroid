package com.itrocket.union.readerPower

import com.itrocket.union.readerPower.data.ReaderPowerRepositoryImpl
import com.itrocket.union.readerPower.domain.ReaderPowerInteractor
import com.itrocket.union.readerPower.domain.dependencies.ReaderPowerRepository
import org.koin.dsl.module

object ReaderPowerModule {

    val module = module {

        factory<ReaderPowerRepository> {
            ReaderPowerRepositoryImpl(get())
        }

        factory {
            ReaderPowerInteractor(get(), get(), get())
        }

    }
}
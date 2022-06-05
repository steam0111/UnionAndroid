package com.itrocket.union.readingMode

import android.os.Bundle
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.core.parameter.parametersOf
import com.itrocket.union.readingMode.data.ReadingModeRepositoryImpl
import com.itrocket.union.readingMode.domain.ReadingModeInteractor
import com.itrocket.union.readingMode.domain.dependencies.ReadingModeRepository
import com.itrocket.union.readingMode.presentation.store.ReadingModeArguments
import com.itrocket.union.readingMode.presentation.store.ReadingModeStore
import com.itrocket.union.readingMode.presentation.store.ReadingModeStoreFactory
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment.Companion.READING_MODE_ARGS

object ReadingModeModule {
    val READINGMODE_VIEW_MODEL_QUALIFIER = named("READINGMODE_VIEW_MODEL")

    val module = module {
        viewModel(READINGMODE_VIEW_MODEL_QUALIFIER) { (args: Bundle) ->
            BaseViewModel(get<ReadingModeStore>() {
                parametersOf(args.getParcelable<ReadingModeArguments>(READING_MODE_ARGS))
            })
        }

        factory<ReadingModeRepository> {
            ReadingModeRepositoryImpl()
        }

        factory {
            ReadingModeInteractor(get(), get(), get())
        }

        factory { (args: ReadingModeArguments) ->
            ReadingModeStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args,
                get()
            ).create()
        }
    }
}
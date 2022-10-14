package com.itrocket.union.changeScanData

import android.os.Bundle
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.core.parameter.parametersOf
import com.itrocket.union.changeScanData.data.ChangeScanDataRepositoryImpl
import com.itrocket.union.changeScanData.domain.ChangeScanDataInteractor
import com.itrocket.union.changeScanData.domain.dependencies.ChangeScanDataRepository
import com.itrocket.union.changeScanData.presentation.store.ChangeScanDataStore
import com.itrocket.union.changeScanData.presentation.store.ChangeScanDataStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.changeScanData.presentation.store.ChangeScanDataArguments
import com.itrocket.union.changeScanData.presentation.view.ChangeScanDataComposeFragment.Companion.CHANGE_SCAN_DATA_ARGS

object ChangeScanDataModule {
    val CHANGESCANDATA_VIEW_MODEL_QUALIFIER = named("CHANGESCANDATA_VIEW_MODEL")

    val module = module {
        viewModel(CHANGESCANDATA_VIEW_MODEL_QUALIFIER) { (args: Bundle) ->
            BaseViewModel(get<ChangeScanDataStore>() {
                parametersOf(args.getParcelable(CHANGE_SCAN_DATA_ARGS))
            })
        }

        factory<ChangeScanDataRepository> {
            ChangeScanDataRepositoryImpl(get())
        }

        factory {
            ChangeScanDataInteractor(get(), get(), get())
        }

        factory { (args: ChangeScanDataArguments) ->
            ChangeScanDataStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args,
                get(),
                get()
            ).create()
        }
    }
}
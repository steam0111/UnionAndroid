package com.itrocket.union.location

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.location.data.LocationRepositoryImpl
import com.itrocket.union.location.domain.LocationInteractor
import com.itrocket.union.location.domain.dependencies.LocationRepository
import com.itrocket.union.location.presentation.store.LocationStore
import com.itrocket.union.location.presentation.store.LocationStoreFactory
import com.itrocket.union.location.presentation.view.LocationComposeFragmentArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object LocationModule {
    val LOCATION_VIEW_MODEL_QUALIFIER = named("LOCATION_VIEW_MODEL")

    val module = module {
        viewModel(LOCATION_VIEW_MODEL_QUALIFIER) { (args: LocationComposeFragmentArgs) ->
            BaseViewModel(get<LocationStore>() {
                parametersOf(args)
            })
        }

        factory<LocationRepository> {
            LocationRepositoryImpl(
                locationSyncApi = get(),
                coreDispatchers = get()
            )
        }

        factory {
            LocationInteractor(get(), get())
        }

        factory { (args: LocationComposeFragmentArgs) ->
            LocationStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                get(),
                get(),
                args.locationArguments
            ).create()
        }
    }
}
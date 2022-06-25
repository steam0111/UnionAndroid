package com.itrocket.union.syncAll

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.syncAll.data.SyncAllRepositoryImpl
import com.itrocket.union.syncAll.domain.SyncAllInteractor
import com.itrocket.union.syncAll.domain.dependencies.SyncAllRepository
import com.itrocket.union.syncAll.presentation.store.SyncAllStore
import com.itrocket.union.syncAll.presentation.store.SyncAllStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object SyncAllModule {
    val SYNCALL_VIEW_MODEL_QUALIFIER = named("SYNCALL_VIEW_MODEL")
    val IS_SYNCED_BD_PREFERENCE_KEY = named("IS_SYNCED_BD_PREFERENCE_KEY")

    val module = module {
        viewModel(SYNCALL_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<SyncAllStore>())
        }

        factory<SyncAllRepository> {
            SyncAllRepositoryImpl(
                get(),
                get(),
                get(),
                isSyncDbPreferenceKey = get(IS_SYNCED_BD_PREFERENCE_KEY)
            )
        }

        factory {
            SyncAllInteractor(get(), get())
        }

        factory {
            SyncAllStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                get()
            ).create()
        }

        single(qualifier = IS_SYNCED_BD_PREFERENCE_KEY) {
            booleanPreferencesKey(IS_SYNCED_BD_PREFERENCE_KEY.value)
        }
    }
}
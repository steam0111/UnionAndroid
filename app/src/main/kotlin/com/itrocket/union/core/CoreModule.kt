package com.itrocket.union.core

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.union_sync_impl.UnionDatabase
import com.itrocket.core.base.AppInsetsStateHolder
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.network.NetworkInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

object CoreModule {

    val module = module {
        single<CoreDispatchers> {
            object : CoreDispatchers {}
        }
        single {
            AppInsetsStateHolder()
        }
        single {
            PreferenceDataStoreFactory.create(
                scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
            ) {
                get<Context>().preferencesDataStoreFile("union_settings")
            }
        }
        single {
            NetworkInfo()
        }
        single(createdAtStart = true) {
            ServerConnectManager(get(), get(), get())
        }
        single(createdAtStart = true) {
            AuthCredentialsManager(get(), get(), get())
        }
        single {
            Room.databaseBuilder(
                get(),
                UnionDatabase::class.java, "union_database"
            ).fallbackToDestructiveMigration().build()
        }
        factory {
            get<UnionDatabase>().nomenclatureGroupDao()
        }
    }
}
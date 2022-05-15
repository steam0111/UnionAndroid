package com.itrocket.union.core

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.itrocket.core.base.AppInsetsStateHolder
import com.itrocket.core.base.CoreDispatchers
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
    }
}
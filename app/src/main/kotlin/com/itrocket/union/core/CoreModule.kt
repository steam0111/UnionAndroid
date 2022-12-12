package com.itrocket.union.core

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.itrocket.core.base.AppInsetsStateHolder
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.nfc.NfcManager
import com.itrocket.union.intentHandler.IntentHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

object CoreModule {

    val UNION_DATA_STORE_QUALIFIER = named("UNION_DATA_STORE_QUALIFIER")

    val module = module {
        single<CoreDispatchers> {
            object : CoreDispatchers {}
        }
        single {
            AppInsetsStateHolder()
        }
        single(UNION_DATA_STORE_QUALIFIER) {
            PreferenceDataStoreFactory.create(
                scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
            ) {
                get<Context>().preferencesDataStoreFile("union_settings")
            }
        }
        single {
            NfcManager()
        }
        single {
            IntentHandler(coreDispatchers = get())
        }
    }
}
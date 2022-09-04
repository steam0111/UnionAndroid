package com.itrocket.union.intentHandler

import android.content.Intent
import com.itrocket.core.base.CoreDispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.withContext

class IntentHandler(private val coreDispatchers: CoreDispatchers) {

    private val intents = MutableSharedFlow<Intent>()

    suspend fun pushIntent(intent: Intent) {
        withContext(coreDispatchers.io) {
            intents.emit(intent)
        }
    }

    fun getIntentFlow() = intents.asSharedFlow()
}
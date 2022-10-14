package com.itrocket.union.container.domain

import android.view.KeyEvent
import com.itrocket.core.base.CoreDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.interid.scannerclient.domain.reader.ReaderMode
import ru.interid.scannerclient_impl.platform.entry.ReadingMode
import ru.interid.scannerclient_impl.platform.entry.ServiceEntry
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class ScannerManager(
    private val serviceEntryManager: ServiceEntryManager,
    private val serviceEntry: ServiceEntry,
    coreDispatchers: CoreDispatchers
) {
    private val coroutineScope = CoroutineScope(coreDispatchers.io + SupervisorJob())

    private var keyCode: Int? = null

    init {
        coroutineScope.launch {
            serviceEntry.initialize()
            serviceEntryManager.changeScanMode(ReadingMode.RFID)
            serviceEntryManager.checkKeyCode()

            launch {
                serviceEntryManager.keyCode.collect {
                    keyCode = it
                }
            }
        }
    }

    fun onKeyDown(
        keyCode: Int,
        event: KeyEvent,
        currentDestinationLabel: String,
        settingsLabel: String
    ): Boolean? {
        return when {
            currentDestinationLabel == settingsLabel && emitKeyEvent(
                keyCode,
                event
            ) -> true
            handleTriggerPressedEvent(keyCode, event) -> true
            else -> null
        }
    }

    fun onKeyUp(keyCode: Int) =
        if (keyUpHandler(keyCode)) true
        else null

    private fun onTriggerDown() = serviceEntryManager.onTriggerPressed()

    private fun onTriggerUp() = serviceEntryManager.onTriggerReleased()

    private fun keyUpHandler(keyCode: Int): Boolean {
        if (keyCode == this.keyCode) onTriggerUp()
        return false
    }

    private fun emitKeyEvent(keyCode: Int, event: KeyEvent): Boolean {
        return serviceEntryManager.deliverKeyCodeEvent(keyCode to event)
    }

    private fun handleTriggerPressedEvent(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == this.keyCode) {
            if (event.eventTime == event.downTime) {
                onTriggerDown()
                return true
            }
            if (event.action == KeyEvent.ACTION_UP) {
                return keyUpHandler(keyCode)
            }
        }
        return false
    }

}
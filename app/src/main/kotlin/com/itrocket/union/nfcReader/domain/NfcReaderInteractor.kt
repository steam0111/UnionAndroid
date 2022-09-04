package com.itrocket.union.nfcReader.domain

import android.content.Intent
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.nfc.NfcManager
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.employeeDetail.domain.EmployeeDetailInteractor
import com.itrocket.union.employees.domain.EmployeeInteractor
import kotlinx.coroutines.withContext

class NfcReaderInteractor(
    private val coreDispatchers: CoreDispatchers,
    private val nfcManager: NfcManager,
    private val authMainInteractor: AuthMainInteractor,
    private val employeeDetailInteractor: EmployeeDetailInteractor
) {

    suspend fun onNewIntent(intent: Intent): Boolean {
        return withContext(coreDispatchers.io) {
            val nfcData = nfcManager.onNfcDataHandled(intent)
            val currentEmployeeId = authMainInteractor.getMyConfig().employeeId
            val currentEmployee =
                if (currentEmployeeId != null) {
                    employeeDetailInteractor.getEmployeeDetail(
                        currentEmployeeId
                    )
                } else {
                    null
                }
            nfcData == currentEmployee?.nfc && nfcData.isNotBlank()
        }
    }
}
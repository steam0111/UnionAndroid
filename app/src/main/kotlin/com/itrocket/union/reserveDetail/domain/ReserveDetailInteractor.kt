package com.itrocket.union.reserveDetail.domain

import com.example.union_sync_api.entity.UpdateTerminalRemainsNumerator
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.sgtin.SgtinFormatter
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.reserves.domain.dependencies.ReservesRepository
import com.itrocket.union.terminalInfo.TerminalInfoRepository
import com.itrocket.union.terminalRemainsNumerator.domain.TerminalRemainsNumeratorDomain
import com.itrocket.union.terminalRemainsNumerator.domain.TerminalRemainsNumeratorRepository
import com.itrocket.union.uniqueDeviceId.data.UniqueDeviceIdRepository
import kotlinx.coroutines.withContext
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.unionPermissions.domain.UnionPermissionsInteractor
import com.itrocket.union.unionPermissions.domain.entity.UnionPermission

class ReserveDetailInteractor(
    private val repository: ReservesRepository,
    private val authMainInteractor: AuthMainInteractor,
    private val terminalRemainsNumeratorRepository: TerminalRemainsNumeratorRepository,
    private val terminalInfoRepository: TerminalInfoRepository,
    private val uniqueDeviceIdRepository: UniqueDeviceIdRepository,
    private val sgtinFormatter: SgtinFormatter,
    private val coreDispatchers: CoreDispatchers,
    private val unionPermissionsInteractor: UnionPermissionsInteractor,
) {
    suspend fun getReserveById(id: String) = withContext(coreDispatchers.io) {
        repository.getReserveById(
            id = id,
            canReadLabelType = unionPermissionsInteractor.canRead(UnionPermission.LABEL_TYPE),
            canUpdateLabelType = unionPermissionsInteractor.canUpdate(UnionPermission.LABEL_TYPE)
        )
    }

    suspend fun getTerminalRemainsNumeratorById(remainsId: String) =
        withContext(coreDispatchers.io) {
            val numerator =
                terminalRemainsNumeratorRepository.getTerminalRemainsNumeratorById(remainsId)
            if (numerator == null) {
                val newNumerator = TerminalRemainsNumeratorDomain(
                    actualNumber = 0,
                    remainsId = remainsId,
                    terminalPrefix = requireNotNull(
                        terminalInfoRepository.getTerminalPrefix()?.toInt()
                    ),
                    terminalId = uniqueDeviceIdRepository.getUniqueDeviceId().id
                )
                terminalRemainsNumeratorRepository.createTerminalRemainsNumerator(
                    numerator = newNumerator,
                    userInserted = authMainInteractor.getLogin()
                )
                newNumerator
            } else {
                numerator
            }
        }

    suspend fun updateActualNumber(remainsId: String, actualNumber: Int) {
        return withContext(coreDispatchers.io) {
            terminalRemainsNumeratorRepository.updateTerminalRemainsNumerator(
                UpdateTerminalRemainsNumerator(
                    remainsId = remainsId,
                    actualNumber = actualNumber + 1
                )
            )
        }
    }

    suspend fun generateSgtinRfid(
        barcode: String?,
        terminalPrefix: Int,
        actualNumber: Int
    ): String = withContext(coreDispatchers.io) {
        val serialNumber = actualNumber.toString() + terminalPrefix.toString()
        val rfid = sgtinFormatter.barcodeToEpcRfid(
            barcode = barcode.orEmpty(),
            serialNumber = serialNumber
        )
        rfid
    }

    suspend fun updateLabelType(
        reserve: ReservesDomain,
        labelTypeId: String
    ): ReservesDomain {
        return withContext(coreDispatchers.io) {
            repository.updateLabelType(reserve = reserve, labelTypeId = labelTypeId)
            getReserveById(reserve.id)
        }
    }
}
package com.itrocket.union.documentCreate.domain

import com.example.union_sync_api.entity.DocumentReserveCountSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateReservesSyncEntity
import com.example.union_sync_api.entity.ReserveShortSyncEntity
import com.example.union_sync_api.entity.ReserveSyncEntity
import com.example.union_sync_api.entity.toDocumentReserveCountSyncEntity
import com.example.union_sync_api.entity.toReserveShortSyncEntity
import com.example.union_sync_api.entity.toReserveUpdateSyncEntity
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.documents.domain.dependencies.DocumentRepository
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.location.domain.dependencies.LocationRepository
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getFilterLocationLastId
import com.itrocket.union.reserves.domain.dependencies.ReservesRepository
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import java.util.*
import kotlinx.coroutines.withContext

class DocumentReservesManager(
    private val coreDispatchers: CoreDispatchers,
    private val reservesRepository: ReservesRepository,
    private val locationRepository: LocationRepository,
    private val documentRepository: DocumentRepository,
    private val authMainInteractor: AuthMainInteractor
) {

    suspend fun changeReservesAfterConduct(
        documentId: String,
        reserves: List<ReservesDomain>,
        documentTypeDomain: DocumentTypeDomain,
        params: List<ParamDomain>
    ) {
        withContext(coreDispatchers.io) {
            val locationId = params.getFilterLocationLastId(ManualType.RELOCATION_LOCATION_TO)
            val newReserves = when (documentTypeDomain) {
                DocumentTypeDomain.GIVE -> changeExtradition(reserves)
                DocumentTypeDomain.RELOCATION -> changeMove(documentId, reserves, locationId)
                else -> {
                    listOf()
                }
            }
            reservesRepository.updateReserves(newReserves.map {
                it.toReserveUpdateSyncEntity(
                    authMainInteractor.getLogin()
                )
            })
        }
    }

    private suspend fun changeExtradition(reserves: List<ReservesDomain>): List<ReserveSyncEntity> {
        return withContext(coreDispatchers.io) {
            val reserveIds = reserves.map { it.id }
            val oldReserves =
                reservesRepository.getReservesByIds(reservesIds = reserveIds).toMutableList()
            changeReserveCount(
                oldReserves = oldReserves,
                reservesDomain = reserves,
                login = authMainInteractor.getLogin()
            )
        }
    }

    private suspend fun changeMove(
        documentId: String,
        reserves: List<ReservesDomain>,
        locationId: String?
    ): List<ReserveSyncEntity> {
        return withContext(coreDispatchers.io) {
            val login = authMainInteractor.getLogin()
            val locationSyncEntity = if (locationId != null) {
                locationRepository.getLocationById(locationId)
            } else {
                null
            }
            val reserveIds = reserves.map { it.id }

            val oldReserves = changeReserveCount(
                oldReserves = reservesRepository.getReservesByIds(reservesIds = reserveIds),
                reservesDomain = reserves,
                login = authMainInteractor.getLogin()
            )
            reservesRepository.updateReserves(oldReserves.map {
                it.toReserveUpdateSyncEntity(
                    authMainInteractor.getLogin()
                )
            })
            val changedOldReserves = oldReserves.map { changedReserve ->
                val reserve = reserves.find { it.id == changedReserve.id }
                changedReserve.copy(
                    count = reserve?.itemsCount,
                    locationSyncEntity = locationSyncEntity
                )
            }
            val existingInNewLocationReservesInfo =
                changedOldReserves.map { it.toReserveShortSyncEntity(locationSyncEntity?.id, authMainInteractor.getLogin()) }

            val existingInNewLocationReserves = getExistingReserves(
                existingInNewLocationReservesInfo = existingInNewLocationReservesInfo,
                changedOldReserves = changedOldReserves
            )
            val newReserves = getNewReserves(
                oldReserves = oldReserves.map { oldReserve ->
                    val reserve = reserves.find { it.id == oldReserve.id }
                    oldReserve.copy(
                        locationSyncEntity = locationSyncEntity,
                        count = reserve?.itemsCount ?: 0,
                        userUpdated = login,
                        userInserted = login,
                    )
                },
                existingInNewLocationReserves
            )

            reservesRepository.insertAll(newReserves)
            existingInNewLocationReserves
        }
    }

    private suspend fun getExistingReserves(
        existingInNewLocationReservesInfo: List<ReserveShortSyncEntity>,
        changedOldReserves: List<ReserveSyncEntity>
    ): List<ReserveSyncEntity> {
        return reservesRepository.getReservesByShorts(reservesShorts = existingInNewLocationReservesInfo)
            .map { existingReserve ->
                val changedReserve = changedOldReserves.find {
                    isSameReserves(
                        oldReserve = it,
                        newReserve = existingReserve
                    )
                }
                if (changedReserve != null) {
                    existingReserve.copy(
                        count = (existingReserve.count ?: 0) + (changedReserve.count ?: 0)
                    )
                } else {
                    existingReserve
                }
            }
    }

    private fun getNewReserves(
        oldReserves: List<ReserveSyncEntity>,
        existingReserves: List<ReserveSyncEntity>
    ): List<ReserveSyncEntity> {
        return oldReserves.filter { oldReserve ->
            val isExistingReserve = existingReserves.find {
                isSameReserves(
                    oldReserve = oldReserve,
                    newReserve = it
                )
            } != null
            !isExistingReserve
        }
    }

    private suspend fun updateDocumentReserveIds(
        newReserves: List<ReserveSyncEntity>
    ) {
        reservesRepository.insertAll(newReserves)
    }

    private fun isSameReserves(
        oldReserve: ReserveSyncEntity,
        newReserve: ReserveSyncEntity
    ): Boolean {
        return oldReserve.name == newReserve.name &&
                oldReserve.nomenclatureId == newReserve.nomenclatureId
                && oldReserve.orderId == newReserve.orderId
                && oldReserve.locationSyncEntity?.id == newReserve.locationSyncEntity?.id
    }

    private suspend fun changeReserveCount(
        oldReserves: List<ReserveSyncEntity>,
        reservesDomain: List<ReservesDomain>,
        login: String?
    ): List<ReserveSyncEntity> {
        val newReserves = oldReserves.map { oldReserve ->
            val reserve = reservesDomain.first { it.id == oldReserve.id }
            val newCount = if (oldReserve.count == null) {
                0
            } else {
                requireNotNull(oldReserve.count) - reserve.itemsCount
            }
            oldReserve.copy(count = newCount, userUpdated = login)
        }
        val documentReserves = oldReserves.map { oldReserve ->
            val reserve = reservesDomain.first { it.id == oldReserve.id }
            oldReserve.copy(count = reserve.itemsCount, userUpdated = login)
        }
        documentRepository.insertDocumentReserveCount(documentReserves.map { it.toDocumentReserveCountSyncEntity() })
        return newReserves
    }
}
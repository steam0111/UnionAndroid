package com.itrocket.union.documentCreate.domain

import android.util.Log
import com.example.union_sync_api.entity.DocumentUpdateReservesSyncEntity
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_api.entity.ReserveShortSyncEntity
import com.example.union_sync_api.entity.ReserveSyncEntity
import com.example.union_sync_api.entity.toDocumentReserveCountSyncEntity
import com.example.union_sync_api.entity.toReserveShortSyncEntity
import com.example.union_sync_api.entity.toReserveUpdateSyncEntity
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.documents.domain.dependencies.DocumentRepository
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.location.domain.dependencies.LocationRepository
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getLocationIds
import com.itrocket.union.reserves.domain.dependencies.ReservesRepository
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import java.util.*
import kotlinx.coroutines.withContext

class DocumentReservesManager(
    private val coreDispatchers: CoreDispatchers,
    private val reservesRepository: ReservesRepository,
    private val locationRepository: LocationRepository,
    private val documentRepository: DocumentRepository
) {

    suspend fun changeReservesAfterConduct(
        documentId: String,
        reserves: List<ReservesDomain>,
        documentTypeDomain: DocumentTypeDomain,
        params: List<ParamDomain>
    ) {
        withContext(coreDispatchers.io) {
            val locationId = params.getLocationIds()?.lastOrNull()
            val newReserves = when (documentTypeDomain) {
                DocumentTypeDomain.EXTRADITION -> changeExtradition(reserves)
                DocumentTypeDomain.MOVING -> changeMove(documentId, reserves, locationId)
                else -> {
                    listOf()
                }
            }
            reservesRepository.updateReserves(newReserves.map { it.toReserveUpdateSyncEntity() })
        }
    }

    private suspend fun changeExtradition(reserves: List<ReservesDomain>): List<ReserveSyncEntity> {
        return withContext(coreDispatchers.io) {
            val reserveIds = reserves.map { it.id }
            val oldReserves =
                reservesRepository.getReservesByIds(reservesIds = reserveIds).toMutableList()
            changeReserveCount(
                oldReserves = oldReserves,
                reservesDomain = reserves
            )
        }
    }

    private suspend fun changeMove(
        documentId: String,
        reserves: List<ReservesDomain>,
        locationId: String?
    ): List<ReserveSyncEntity> {
        return withContext(coreDispatchers.io) {
            val locationSyncEntity = if (locationId != null) {
                locationRepository.getLocationById(locationId)
            } else {
                null
            }
            val reserveIds = reserves.map { it.id }

            val oldReserves = changeReserveCount(
                oldReserves = reservesRepository.getReservesByIds(reservesIds = reserveIds),
                reservesDomain = reserves
            )
            reservesRepository.updateReserves(oldReserves.map { it.toReserveUpdateSyncEntity() })
            val changedOldReserves = oldReserves.map { changedReserve ->
                val reserve = reserves.find { it.id == changedReserve.id }
                changedReserve.copy(
                    count = reserve?.itemsCount,
                    locationSyncEntity = locationSyncEntity
                )
            }
            val existingInNewLocationReservesInfo =
                changedOldReserves.map { it.toReserveShortSyncEntity(locationSyncEntity?.id) }

            val existingInNewLocationReserves = getExistingReserves(
                existingInNewLocationReservesInfo = existingInNewLocationReservesInfo,
                changedOldReserves = changedOldReserves
            )

            val newReserves = getNewReserves(
                oldReserves = oldReserves.map { oldReserve ->
                    val reserve = reserves.find { it.id == oldReserve.id }
                    oldReserve.copy(
                        locationSyncEntity = locationSyncEntity,
                        count = reserve?.itemsCount ?: 0
                    )
                },
                existingInNewLocationReserves
            )

            val mappedNewReserves = newReserves.map { newReserve ->
                newReserve.copy(
                    id = UUID.randomUUID().toString(),
                    count = newReserve.count,
                    locationSyncEntity = locationSyncEntity
                )
            }
            updateDocumentReserveIds(
                documentId = documentId,
                existingReserves = existingInNewLocationReserves,
                newReserves = mappedNewReserves
            )
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
        documentId: String,
        existingReserves: List<ReserveSyncEntity>,
        newReserves: List<ReserveSyncEntity>
    ) {
        val reservesIds = existingReserves.map { it.id } + newReserves.map { it.id }
        reservesRepository.insertAll(newReserves)
        val documentUpdateReserves =
            DocumentUpdateReservesSyncEntity(id = documentId, reservesIds = reservesIds)
        documentRepository.updateDocumentReserves(documentUpdateReserves)
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
        reservesDomain: List<ReservesDomain>
    ): List<ReserveSyncEntity> {
        val newReserves = oldReserves.map { oldReserve ->
            val reserve = reservesDomain.first { it.id == oldReserve.id }
            val newCount = if (oldReserve.count == null) {
                0
            } else {
                requireNotNull(oldReserve.count) - reserve.itemsCount
            }
            oldReserve.copy(count = newCount)
        }
        val documentReserves = oldReserves.map { oldReserve ->
            val reserve = reservesDomain.first { it.id == oldReserve.id }
            oldReserve.copy(count = reserve.itemsCount)
        }
        documentRepository.insertDocumentReserveCount(documentReserves.map { it.toDocumentReserveCountSyncEntity() })
        return newReserves
    }
}
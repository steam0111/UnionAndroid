package com.itrocket.union.documentCreate.domain

import com.example.union_sync_api.entity.ReserveShortSyncEntity
import com.example.union_sync_api.entity.ReserveSyncEntity
import com.example.union_sync_api.entity.toReserveCountSyncEntity
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
import kotlinx.coroutines.withContext
import java.math.BigDecimal

class DocumentReservesManager(
    private val coreDispatchers: CoreDispatchers,
    private val reservesRepository: ReservesRepository,
    private val locationRepository: LocationRepository,
    private val documentRepository: DocumentRepository,
    private val authMainInteractor: AuthMainInteractor
) {

    suspend fun changeReservesAfterConduct(
        reserves: List<ReservesDomain>,
        documentTypeDomain: DocumentTypeDomain,
        params: List<ParamDomain>
    ) {
        withContext(coreDispatchers.io) {
            val locationId = params.getFilterLocationLastId(ManualType.RELOCATION_LOCATION_TO)
            val newReserves = when (documentTypeDomain) {
                DocumentTypeDomain.GIVE -> changeExtradition(reserves)
                DocumentTypeDomain.RELOCATION -> changeMove(reserves, locationId)
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
                login = login
            )
            reservesRepository.updateReserves(oldReserves.map {
                it.toReserveUpdateSyncEntity(
                    login
                )
            })
            val changedOldReserves = oldReserves.map { changedReserve ->
                val reserve = reserves.find { it.id == changedReserve.id }
                changedReserve.copy(
                    count = reserve?.itemsCount,
                    locationSyncEntity = listOfNotNull(locationSyncEntity)
                )
            }
            val existingInNewLocationReservesInfo =
                changedOldReserves.map { it.toReserveShortSyncEntity(locationSyncEntity?.id, login) }

            val existingInNewLocationReserves = getExistingReserves(
                existingInNewLocationReservesInfo = existingInNewLocationReservesInfo,
                changedOldReserves = changedOldReserves
            )
            val newReserves = getNewReserves(
                oldReserves = oldReserves.map { oldReserve ->
                    val reserve = reserves.find { it.id == oldReserve.id }
                    oldReserve.copy(
                        locationSyncEntity = listOfNotNull(locationSyncEntity),
                        count = reserve?.itemsCount ?: BigDecimal.ZERO,
                        userUpdated = login,
                        userInserted = login,
                        updateDate = System.currentTimeMillis()
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
                        count = (existingReserve.count ?: BigDecimal.ZERO) + (changedReserve.count ?: BigDecimal.ZERO)
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

    private fun isSameReserves(
        oldReserve: ReserveSyncEntity,
        newReserve: ReserveSyncEntity
    ): Boolean {
        return oldReserve.name == newReserve.name &&
                oldReserve.nomenclatureId == newReserve.nomenclatureId
                && oldReserve.orderId == newReserve.orderId
                && oldReserve.locationSyncEntity?.lastOrNull()?.id == newReserve.locationSyncEntity?.lastOrNull()?.id
    }

    private suspend fun changeReserveCount(
        oldReserves: List<ReserveSyncEntity>,
        reservesDomain: List<ReservesDomain>,
        login: String?
    ): List<ReserveSyncEntity> {
        val newReserves = oldReserves.map { oldReserve ->
            val reserve = reservesDomain.first { it.id == oldReserve.id }
            val newCount = if (oldReserve.count == null) {
                BigDecimal.ZERO
            } else {
                requireNotNull(oldReserve.count) - reserve.itemsCount
            }
            oldReserve.copy(count = newCount, userUpdated = login)
        }
        val documentReserves = oldReserves.map { oldReserve ->
            val reserve = reservesDomain.first { it.id == oldReserve.id }
            oldReserve.copy(count = reserve.itemsCount, userUpdated = login)
        }
        documentRepository.insertDocumentReserveCount(documentReserves.map { it.toReserveCountSyncEntity() })
        return newReserves
    }
}
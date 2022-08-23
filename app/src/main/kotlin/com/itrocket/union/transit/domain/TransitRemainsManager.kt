package com.itrocket.union.transit.domain

import com.example.union_sync_api.entity.ReserveCountSyncEntity
import com.example.union_sync_api.entity.ReserveShortSyncEntity
import com.example.union_sync_api.entity.ReserveSyncEntity
import com.example.union_sync_api.entity.TransitUpdateReservesSyncEntity
import com.example.union_sync_api.entity.toReserveCountSyncEntity
import com.example.union_sync_api.entity.toReserveShortSyncEntity
import com.example.union_sync_api.entity.toReserveUpdateSyncEntity
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.location.domain.dependencies.LocationRepository
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getFilterLocationLastId
import com.itrocket.union.reserves.domain.dependencies.ReservesRepository
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.transit.domain.dependencies.TransitRepository
import kotlinx.coroutines.withContext

class TransitRemainsManager(
    private val coreDispatchers: CoreDispatchers,
    private val reservesRepository: ReservesRepository,
    private val locationRepository: LocationRepository,
    private val transitRepository: TransitRepository,
    private val authMainInteractor: AuthMainInteractor
) {

    suspend fun conductReserve(
        reserves: List<ReservesDomain>,
        params: List<ParamDomain>,
        transitTypeDomain: TransitTypeDomain
    ) {
        val vehicleId = params.getFilterLocationLastId(ManualType.TRANSIT)
        val locationToId = params.getFilterLocationLastId(ManualType.LOCATION_TO)
        val newReserves =
            changeTransit(
                reserves = reserves,
                vehicleId = vehicleId,
                locationToId = locationToId,
                transitTypeDomain = transitTypeDomain
            )
        reservesRepository.updateReserves(newReserves.map {
            it.toReserveUpdateSyncEntity(
                authMainInteractor.getLogin()
            )
        })
    }

    private suspend fun changeTransit(
        reserves: List<ReservesDomain>,
        vehicleId: String?,
        locationToId: String?,
        transitTypeDomain: TransitTypeDomain
    ): List<ReserveSyncEntity> {
        return withContext(coreDispatchers.io) {
            val login = authMainInteractor.getLogin()
            val locationSyncEntity = when {
                vehicleId != null && transitTypeDomain == TransitTypeDomain.TRANSIT_SENDING -> locationRepository.getLocationById(
                    vehicleId
                )
                locationToId != null && transitTypeDomain == TransitTypeDomain.TRANSIT_RECEPTION -> locationRepository.getLocationById(
                    locationToId
                )
                else -> null
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
                    locationSyncEntity = locationSyncEntity
                )
            }
            val existingInNewLocationReservesInfo =
                changedOldReserves.map {
                    it.toReserveShortSyncEntity(
                        locationSyncEntity?.id,
                        login
                    )
                }

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
        val transitReserves = oldReserves.map { oldReserve ->
            val reserve = reservesDomain.first { it.id == oldReserve.id }
            oldReserve.copy(count = reserve.itemsCount, userUpdated = login)
        }
        transitRepository.insertTransitReserveCount(transitReserves.map { it.toReserveCountSyncEntity() })
        return newReserves
    }
}
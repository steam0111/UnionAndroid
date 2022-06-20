package com.example.union_sync_impl.data

import com.example.union_sync_api.data.LocationSyncApi
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.NetworkSyncDao
import com.example.union_sync_impl.data.mapper.toLocationDb
import com.example.union_sync_impl.data.mapper.toLocationSyncEntity
import com.example.union_sync_impl.data.mapper.toLocationTypeDb
import com.example.union_sync_impl.entity.NetworkSyncDb
import org.openapitools.client.custom_api.LocationApi
import org.openapitools.client.models.CustomLocationDto
import org.openapitools.client.models.CustomLocationsTypeDto

class LocationSyncApiImpl(
    private val locationApi: LocationApi,
    private val locationDao: LocationDao,
    private val networkSyncDao: NetworkSyncDao
) : LocationSyncApi {

    override suspend fun getLocations(locationTypeParentId: String?): List<LocationSyncEntity> {
        syncLocations()

        val locationType = locationDao.getNextLocationTypeByParent(locationTypeParentId)
        val locations = locationDao.getLocationsByType(locationType.id)

        return locations.map {
            it.toLocationSyncEntity(locationType)
        }
    }

    private suspend fun syncLocations() {
        val networkSyncDb = networkSyncDao.getNetworkSync()
        if (networkSyncDb?.isLocationsSync != true) {
            val networkLocationTypes: List<CustomLocationsTypeDto> = requireNotNull(locationApi.apiLocationsTypesGet().list)
            locationDao.insertAllLocationTypes(networkLocationTypes.map { it.toLocationTypeDb() })

            val networkLocations: List<CustomLocationDto> = requireNotNull(locationApi.apiLocationsGet().list)
            locationDao.insertAll(networkLocations.map { it.toLocationDb() })

            networkSyncDao.insert(
                networkSyncDb?.copy(isLocationsSync = true) ?: NetworkSyncDb(
                    isLocationsSync = true
                )
            )
        }
    }
}
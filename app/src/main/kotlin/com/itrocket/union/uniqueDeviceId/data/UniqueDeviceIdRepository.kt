package com.itrocket.union.uniqueDeviceId.data

interface UniqueDeviceIdRepository {
    suspend fun getUniqueDeviceId(): UniqueDeviceId
    suspend fun saveDeviceId(id: String)
    suspend fun clearDeviceId()
}
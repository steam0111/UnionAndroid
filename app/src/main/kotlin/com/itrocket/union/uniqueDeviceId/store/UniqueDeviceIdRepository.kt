package com.itrocket.union.uniqueDeviceId.store

interface UniqueDeviceIdRepository {
    suspend fun getUniqueDeviceId(): UniqueDeviceId
}
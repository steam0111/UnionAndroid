package com.example.union_sync_impl.data

import com.example.union_sync_api.data.CounterpartySyncApi
import com.example.union_sync_api.entity.CounterpartySyncEntity
import com.example.union_sync_impl.dao.CounterpartyDao
import com.example.union_sync_impl.data.mapper.toSyncEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CounterpartySyncApiImpl(
    val counterpartyDao: CounterpartyDao
) : CounterpartySyncApi {
    override suspend fun getCounterparties(): Flow<List<CounterpartySyncEntity>> {
        return flow {
            emit(counterpartyDao.getAll().map { it.toSyncEntity() })
        }.distinctUntilChanged().flowOn(Dispatchers.IO)
    }

    override suspend fun getCounterpartyDetail(id: String): CounterpartySyncEntity {
        return counterpartyDao.getById(id).toSyncEntity()
    }
}
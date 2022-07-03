package com.example.union_sync_impl.data

import com.example.union_sync_api.data.OrderSyncApi
import com.example.union_sync_api.entity.OrderSyncEntity
import com.example.union_sync_impl.dao.OrderDao
import com.example.union_sync_impl.dao.sqlOrderQuery
import com.example.union_sync_impl.data.mapper.toSyncEntity

class OrderSyncApiImpl(private val orderDao: OrderDao) : OrderSyncApi {

    override suspend fun getAll(textQuery: String?): List<OrderSyncEntity> {
        return orderDao.getAll(
            sqlOrderQuery(textQuery = textQuery)
        ).map { it.toSyncEntity() }
    }

}
package com.example.union_sync_api.data

import com.example.union_sync_api.entity.InventoryRecordSyncEntity

interface InventoryRecordSyncApi {

    fun getAll(inventoryId: String? = null): List<InventoryRecordSyncEntity>
}
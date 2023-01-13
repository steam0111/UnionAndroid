package com.example.union_sync_api.data

import com.example.union_sync_api.entity.InventoryNomenclatureRecordSyncEntity

interface InventoryNomenclatureRecordSyncApi {

    fun getAll(inventoryId: String? = null): List<InventoryNomenclatureRecordSyncEntity>
}
package com.example.union_sync_impl.data

import com.example.union_sync_api.data.ClearSyncDataApi
import com.example.union_sync_impl.UnionDatabase

class ClearSyncDataImpl(
    private val unionDatabase: UnionDatabase
) : ClearSyncDataApi {

    override suspend fun clearAll() {
        unionDatabase.clearAllTables()
    }
}
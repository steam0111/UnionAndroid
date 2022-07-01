package com.example.union_sync_api.data

import com.example.union_sync_api.entity.ReceptionItemCategorySyncEntity

interface ReceptionItemCategorySyncApi {
    suspend fun getAll(textQuery: String? = null): List<ReceptionItemCategorySyncEntity>
}
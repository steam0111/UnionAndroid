package com.example.union_sync_api.entity

sealed class SyncInfoType {
    data class ItemCount(
        val count: Long?,
        val isAllCount: Boolean
    ) : SyncInfoType()

    data class TitleResourceEvent(
        val titleId: Int,
        val syncDirection: SyncDirection,
    ) : SyncInfoType()

    data class TitleEvent(val title: String) : SyncInfoType()
}

enum class SyncDirection {
    FROM_SERVER, TO_SERVER
}
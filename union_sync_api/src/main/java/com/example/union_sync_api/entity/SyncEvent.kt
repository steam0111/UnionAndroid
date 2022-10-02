package com.example.union_sync_api.entity

import kotlin.time.Duration

sealed class SyncEvent(open val id: String, open val name: String) {
    data class Info(override val name: String, override val id: String) :
        SyncEvent(id = id, name = name)

    data class Measured(
        override val id: String,
        override val name: String,
        val duration: Duration
    ) : SyncEvent(id = id, name = name)

    data class Error(
        override val name: String, override val id: String
    ) : SyncEvent(id = id, name = name)
}
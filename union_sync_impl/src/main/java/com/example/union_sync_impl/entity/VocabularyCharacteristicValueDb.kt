package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.CatalogItemDb

@Entity(tableName = "vocabularyCharacteristicValue")
class VocabularyCharacteristicValueDb(
    id: String,
    cancel: Boolean? = false,
    insertDate: Long?,
    updateDate: Long?,
    userUpdated: String?,
    userInserted: String?,
    override var catalogItemName: String,
    val value: String?
) : CatalogItemDb(
    id = id,
    cancel = cancel,
    insertDate = insertDate,
    updateDate = updateDate,
    userUpdated = userUpdated,
    userInserted = userInserted
)
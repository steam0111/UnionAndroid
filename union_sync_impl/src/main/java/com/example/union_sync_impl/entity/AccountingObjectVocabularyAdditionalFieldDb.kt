package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.CatalogItemDb

@Entity(tableName = "accountingObjectVocabularyAdditionalField")
class AccountingObjectVocabularyAdditionalFieldDb(
    id: String,
    insertDate: Long?,
    updateDate: Long?,
    userUpdated: String?,
    userInserted: String?,
    override var catalogItemName: String,
    val vocabularyAdditionalFieldValueId: String?,
    val vocabularyAdditionalFieldId: String?,
    val accountingObjectId: String?
) : CatalogItemDb(
    id = id,
    insertDate = insertDate,
    updateDate = updateDate,
    userUpdated = userUpdated,
    userInserted = userInserted
)
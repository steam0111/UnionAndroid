package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.AccountingObjectSimpleAdditionalFieldDb
import com.example.union_sync_impl.entity.AccountingObjectVocabularyAdditionalFieldDb
import com.example.union_sync_impl.entity.FullAccountingObjectSimpleAdditionalFieldDb
import com.example.union_sync_impl.entity.FullAccountingObjectVocabularyAdditionalFieldDb

@Dao
interface AccountingObjectVocabularyAdditionalFieldDao {

    @Query(
        "SELECT accountingObjectVocabularyAdditionalField.*," +
                "" +
                "nameField.id AS nameField_id, " +
                "nameField.catalogItemName AS nameField_catalogItemName, " +
                "nameField.name AS nameField_name, " +
                "" +
                "valueField.id AS valueField_id, " +
                "valueField.catalogItemName AS valueField_catalogItemName, " +
                "valueField.value AS valueField_value " +
                "" +
                "FROM accountingObjectVocabularyAdditionalField " +
                "LEFT JOIN vocabularyAdditionalField nameField ON accountingObjectVocabularyAdditionalField.vocabularyAdditionalFieldId = nameField.id " +
                "LEFT JOIN vocabularyAdditionalFieldValue valueField ON accountingObjectVocabularyAdditionalField.vocabularyAdditionalFieldValueId = valueField.id " +
                "WHERE accountingObjectId = :accountingObjectId"
    )
    suspend fun getAll(accountingObjectId: String): List<FullAccountingObjectVocabularyAdditionalFieldDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(checkers: List<AccountingObjectVocabularyAdditionalFieldDb>)
}
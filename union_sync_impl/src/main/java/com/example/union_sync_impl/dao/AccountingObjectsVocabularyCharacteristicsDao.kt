package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.AccountingObjectVocabularyCharacteristicsDb
import com.example.union_sync_impl.entity.FullAccountingObjectVocabularyCharacteristicDb

@Dao
interface AccountingObjectsVocabularyCharacteristicsDao {

    @Query(
        "SELECT accountingObjectsVocabularyCharacteristicValue.*," +
                "" +
                "nameField.id AS nameField_id, " +
                "nameField.catalogItemName AS nameField_catalogItemName, " +
                "nameField.name AS nameField_name, " +
                "" +
                "valueField.id AS valueField_id, " +
                "valueField.catalogItemName AS valueField_catalogItemName, " +
                "valueField.value AS valueField_value " +
                "" +
                "FROM accountingObjectsVocabularyCharacteristicValue " +
                "LEFT JOIN vocabularyCharacteristic nameField ON accountingObjectsVocabularyCharacteristicValue.vocabularyCharacteristicId = nameField.id " +
                "LEFT JOIN vocabularyCharacteristicValue valueField ON accountingObjectsVocabularyCharacteristicValue.vocabularyCharacteristicValueId = valueField.id " +
                "WHERE accountingObjectId = :accountingObjectId AND accountingObjectsVocabularyCharacteristicValue.cancel != 1"
    )
    suspend fun getAll(accountingObjectId: String): List<FullAccountingObjectVocabularyCharacteristicDb>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(checkers: List<AccountingObjectVocabularyCharacteristicsDb>)
}
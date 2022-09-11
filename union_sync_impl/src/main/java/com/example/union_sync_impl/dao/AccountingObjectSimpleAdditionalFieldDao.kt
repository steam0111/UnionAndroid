package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.AccountingObjectSimpleAdditionalFieldDb
import com.example.union_sync_impl.entity.FullAccountingObjectSimpleAdditionalFieldDb

@Dao
interface AccountingObjectSimpleAdditionalFieldDao {

    @Query(
        "SELECT accountingObjectSimpleAdditionalFieldValue.*," +
                "" +
                "nameField.id AS nameField_id, " +
                "nameField.catalogItemName AS nameField_catalogItemName, " +
                "nameField.name AS nameField_name " +
                "" +
                "FROM accountingObjectSimpleAdditionalFieldValue " +
                "LEFT JOIN simpleAdditionalField nameField ON accountingObjectSimpleAdditionalFieldValue.simpleAdditionalFieldId = nameField.id " +
                "WHERE accountingObjectId = :accountingObjectId"
    )
    suspend fun getAll(accountingObjectId: String): List<FullAccountingObjectSimpleAdditionalFieldDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(checkers: List<AccountingObjectSimpleAdditionalFieldDb>)
}
package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.AccountingObjectSimpleCharacteristicsDb
import com.example.union_sync_impl.entity.FullAccountingObjectSimpleCharacteristicDb

@Dao
interface AccountingObjectsSimpleCharacteristicsDao {

    @Query(
        "SELECT accountingObjectsSimpleCharacteristicValue.*," +
                "" +
                "nameField.id AS nameField_id, " +
                "nameField.catalogItemName AS nameField_catalogItemName, " +
                "nameField.name AS nameField_name " +
                "" +
                "FROM accountingObjectsSimpleCharacteristicValue " +
                "LEFT JOIN simpleCharacteristic nameField ON accountingObjectsSimpleCharacteristicValue.simpleCharacteristicId = nameField.id " +
                "WHERE accountingObjectId = :accountingObjectId AND accountingObjectsSimpleCharacteristicValue.cancel != 1"
    )
    suspend fun getAll(accountingObjectId: String): List<FullAccountingObjectSimpleCharacteristicDb>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(checkers: List<AccountingObjectSimpleCharacteristicsDb>)
}
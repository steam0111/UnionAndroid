package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.FullDocument
import com.example.union_sync_impl.entity.FullTransit
import com.example.union_sync_impl.entity.TransitDb
import kotlinx.coroutines.flow.Flow

@Dao
interface TransitDao {

    @RawQuery(observedEntities = [FullTransit::class])
    fun getAll(query: SupportSQLiteQuery): Flow<List<FullTransit>>

    @RawQuery
    fun getCount(query: SupportSQLiteQuery): Long

    @Query(
        "SELECT transit.*," +
                "" +
                "molEmployees.id AS mol_id, " +
                "molEmployees.catalogItemName AS mol_catalogItemName, " +
                "molEmployees.firstname AS mol_firstname, " +
                "molEmployees.lastname AS mol_lastname, " +
                "molEmployees.patronymic AS mol_patronymic, " +
                "molEmployees.number AS mol_number, " +
                "molEmployees.nfc AS mol_nfc, " +
                "" +
                "receivingEmployees.id AS receiving_id, " +
                "receivingEmployees.catalogItemName AS receiving_catalogItemName, " +
                "receivingEmployees.firstname AS receiving_firstname, " +
                "receivingEmployees.lastname AS receiving_lastname, " +
                "receivingEmployees.patronymic AS receiving_patronymic, " +
                "receivingEmployees.number AS receiving_number, " +
                "receivingEmployees.nfc AS receiving_nfc " +
                "" +
                "FROM transit " +
                "LEFT JOIN employees molEmployees ON transit.molId = molEmployees.id " +
                "LEFT JOIN employees receivingEmployees ON transit.receivingId = receivingEmployees.id " +
                "WHERE transit.id = :id LIMIT 1 "
    )
    suspend fun getTransitById(id: String): FullTransit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transitDb: TransitDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(transits: List<TransitDb>)

    @Update
    suspend fun update(transitDb: TransitDb)

    @Query("DELETE FROM transit")
    suspend fun clearAll()
}
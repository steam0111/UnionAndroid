package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.location.LocationDb
import com.example.union_sync_impl.entity.location.LocationTypeDb

@Dao
interface LocationDao {
    @Query("SELECT * FROM location WHERE parentId is :parentId AND cancel != 1")
    suspend fun getAll(parentId: String?): List<LocationDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg locationDbs: LocationDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locationDbs: List<LocationDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLocationTypes(locationTypesDbs: List<LocationTypeDb>)

    @Query("SELECT * FROM locationTypes WHERE parentId is :parentId LIMIT 1")
    suspend fun getNextLocationTypeByParent(parentId: String?): LocationTypeDb

    @Query("SELECT * FROM locationTypes WHERE id is :id LIMIT 1")
    suspend fun getLocationTypeById(id: String): LocationTypeDb?

    @Query("SELECT * FROM location WHERE id IN (:ids)")
    suspend fun getLocationsByIds(ids: List<String?>): List<LocationDb>

    @Query("SELECT * FROM location WHERE id is :id LIMIT 1")
    suspend fun getLocationById(id: String?): LocationDb?

    @Query("SELECT * FROM location WHERE locationTypeId is :locationTypeId")
    suspend fun getLocationsByType(locationTypeId: String): List<LocationDb>

    /**
     * 1) Строиться общая таблица location + locationPath
     * 2) Вытаскиваются вcе дочерние location по id родителя
     */
    @Query(
        """
            SELECT * FROM `location` `location` 
                JOIN locationPath `closure`
                ON `location`.`id` = `closure`.`descendantLocationId` 
            WHERE `closure`.`ancestorLocationId` is :parentId
            """
    )
    suspend fun getAllLocationsByParentId(parentId: String?): List<LocationDb>

    @RawQuery
    fun getLocationsByParentId(query: SupportSQLiteQuery): List<LocationDb>
}
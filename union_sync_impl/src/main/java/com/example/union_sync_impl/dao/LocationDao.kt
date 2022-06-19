package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.location.LocationDb
import com.example.union_sync_impl.entity.location.LocationTypeDb

@Dao
interface LocationDao {
    @Query("SELECT * FROM location WHERE parentId is :parentId")
    suspend fun getAll(parentId: String?): List<LocationDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg locationDbs: LocationDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locationDbs: List<LocationDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLocationTypes(locationTypesDbs: List<LocationTypeDb>)

    @Query("SELECT * FROM locationTypes WHERE parentId is :parentId LIMIT 1")
    suspend fun getLocationType(parentId: String?): LocationTypeDb

    @Query("SELECT * FROM location WHERE id IN (:ids)")
    suspend fun getLocationsByIds(ids: List<String>): List<LocationDb>

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
            WHERE `closure`.`ancestorLocationId` = :parentId
            """
    )
    suspend fun getAllLocationByParentId(parentId: Long): List<LocationDb>
}
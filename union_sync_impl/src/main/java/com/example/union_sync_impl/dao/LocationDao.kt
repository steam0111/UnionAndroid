package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.location.LocationDb

@Dao
interface LocationDao {
    @Query("SELECT * FROM location WHERE parentId is :parentId")
    suspend fun getAll(parentId: String?): List<LocationDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg locationDbs: LocationDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locationDbs: List<LocationDb>)

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
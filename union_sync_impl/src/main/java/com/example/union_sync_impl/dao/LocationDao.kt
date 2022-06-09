package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.union_sync_impl.entity.location.Location

@Dao
interface LocationDao {
    @Query("SELECT * FROM location WHERE parentId is :parentId")
    suspend fun getAll(parentId: Long?): List<Location>

    @Insert
    suspend fun insertAll(vararg locations: Location)

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
    suspend fun getAllLocationByParentId(parentId: Long): List<Location>
}
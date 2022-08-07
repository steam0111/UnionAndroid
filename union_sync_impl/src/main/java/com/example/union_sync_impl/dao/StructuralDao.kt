package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.structural.StructuralDb

@Dao
interface StructuralDao {

    @Query("SELECT * FROM structural WHERE parentId is :parentId")
    suspend fun getAll(parentId: String?): List<StructuralDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg structuralDbs: StructuralDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(structuralDbs: List<StructuralDb>)

    @Query("SELECT * FROM structural WHERE id IN (:ids)")
    suspend fun getStructuralsByIds(ids: List<String?>): List<StructuralDb>

    @Query("SELECT * FROM structural WHERE id is :id LIMIT 1")
    suspend fun getStructuralById(id: String?): StructuralDb?

    /**
     * 1) Строиться общая таблица structural + structuralPath
     * 2) Вытаскиваются вcе дочерние structural по id родителя
     */
    @Query(
        """
            SELECT * FROM `structural` `structural` 
                JOIN structuralPath `closure`
                ON `structural`.`id` = `closure`.`descendantStructuralId` 
            WHERE `closure`.`ancestorStructuralId` is :parentId
            """
    )
    suspend fun getAllStructuralsByParentId(parentId: String?): List<StructuralDb>

    @RawQuery
    fun getStructuralsByParentId(query: SupportSQLiteQuery): List<StructuralDb>
}
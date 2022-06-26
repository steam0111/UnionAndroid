package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.FullNomenclatureDb
import com.example.union_sync_impl.entity.NomenclatureDb

@Dao
interface NomenclatureDao {

    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<NomenclatureDb>

    @Query(
        "SELECT nomenclature.*," +
                "nomenclature_group.id AS groups_id, " +
                "nomenclature_group.catalogItemName AS groups_catalogItemName, " +
                "nomenclature_group.name AS groups_name " +
                "" +
                "FROM nomenclature " +
                "LEFT JOIN nomenclature_group ON nomenclature.nomenclatureGroupId = nomenclature_group.id " +
                "WHERE nomenclature.id = :id LIMIT 1"
    )
    suspend fun getById(id: String): FullNomenclatureDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg nomenclatureDbs: NomenclatureDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(nomenclatureDbs: List<NomenclatureDb>)
}
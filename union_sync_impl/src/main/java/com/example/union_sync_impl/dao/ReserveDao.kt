package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.FullReserve
import com.example.union_sync_impl.entity.ReserveDb
import com.example.union_sync_impl.entity.ReserveLabelTypeUpdate
import com.example.union_sync_impl.entity.ReserveUpdate

@Dao
interface ReserveDao {

    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<FullReserve>

    @RawQuery
    fun getAllSimpled(query: SupportSQLiteQuery): List<ReserveDb>

    @RawQuery
    suspend fun getFilterCount(query: SupportSQLiteQuery): Long

    @Query(
        "SELECT reserves.*," +
                "" +
                "structural.id AS structural_id, " +
                "structural.catalogItemName AS structural_catalogItemName, " +
                "structural.name AS structural_name, " +
                "structural.parentId AS structural_parentId, " +
                "" +
                "molEmployees.id AS mol_id, " +
                "molEmployees.catalogItemName AS mol_catalogItemName, " +
                "molEmployees.firstname AS mol_firstname, " +
                "molEmployees.lastname AS mol_lastname, " +
                "molEmployees.patronymic AS mol_patronymic, " +
                "molEmployees.structuralId AS mol_structuralId, " +
                "molEmployees.number AS mol_number, " +
                "molEmployees.nfc AS mol_nfc, " +
                "" +
                "nomenclature.id AS nomenclature_id, " +
                "nomenclature.catalogItemName AS nomenclature_catalogItemName, " +
                "nomenclature.name AS nomenclature_name, " +
                "nomenclature.number AS nomenclature_number, " +
                "nomenclature.nomenclatureGroupId AS nomenclature_nomenclatureGroupId, " +
                "" +
                "nomenclature_group.id AS nomenclatureGroup_id, " +
                "nomenclature_group.catalogItemName AS nomenclatureGroup_catalogItemName, " +
                "nomenclature_group.name AS nomenclatureGroup_name, " +
                "" +
                "reception_item_category.id AS receptionItemCategory_id, " +
                "reception_item_category.name AS receptionItemCategory_name, " +
                "" +
                "location.id AS locations_id, " +
                "location.catalogItemName AS locations_catalogItemName, " +
                "location.name AS locations_name, " +
                "location.parentId AS locations_parentId, " +
                "" +
                "locationTypes.id AS location_type_id, " +
                "locationTypes.catalogItemName AS location_type_catalogItemName, " +
                "locationTypes.name AS location_type_name, " +
                "locationTypes.parentId AS location_type_parentId, " +
                "" +
                "label_type.id AS label_type_id, " +
                "label_type.name AS label_type_name," +
                "label_type.description AS label_type_description," +
                "label_type.code as label_type_code," +
                "label_type.catalogItemName AS label_type_catalogItemName " +
                "" +
                "FROM reserves " +
                "LEFT JOIN location ON reserves.locationId = location.id " +
                "LEFT JOIN employees molEmployees ON reserves.molId = molEmployees.id " +
                "LEFT JOIN nomenclature ON reserves.nomenclatureId = nomenclature.id " +
                "LEFT JOIN nomenclature_group ON reserves.nomenclatureGroupId = nomenclature_group.id " +
                "LEFT JOIN reception_item_category ON reserves.receptionItemCategoryId = reception_item_category.id " +
                "LEFT JOIN locationTypes ON reserves.locationTypeId = locationTypes.id " +
                "LEFT JOIN label_type ON reserves.labelTypeId = label_type.id " +
                "LEFT JOIN structural ON reserves.structuralId = structural.id " +
                "WHERE reserves.id = :id LIMIT 1"
    )
    suspend fun getById(id: String): FullReserve

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(reserves: List<ReserveDb>)

    @Update(entity = ReserveDb::class)
    suspend fun update(reserveUpdates: List<ReserveUpdate>)

    @Update(entity = ReserveDb::class)
    suspend fun update(ReserveDb: ReserveLabelTypeUpdate)
}
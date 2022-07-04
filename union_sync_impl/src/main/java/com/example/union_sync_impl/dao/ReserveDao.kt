package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.FullReserve
import com.example.union_sync_impl.entity.ReserveDb

@Dao
interface ReserveDao {

    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<FullReserve>

    @RawQuery
    suspend fun getFilterCount(query: SupportSQLiteQuery): Int

    @Query(
        "SELECT reserves.*," +
                "organizations.id AS businessUnit_id, " +
                "organizations.catalogItemName AS businessUnit_catalogItemName, " +
                "organizations.name AS businessUnit_name, " +
                "organizations.actualAddress AS businessUnit_actualAddress, " +
                "organizations.legalAddress AS businessUnit_legalAddress, " +
                "" +
                "molEmployees.id AS mol_id, " +
                "molEmployees.catalogItemName AS mol_catalogItemName, " +
                "molEmployees.firstname AS mol_firstname, " +
                "molEmployees.lastname AS mol_lastname, " +
                "molEmployees.patronymic AS mol_patronymic, " +
                "molEmployees.organizationId AS mol_organizationId, " +
                "molEmployees.number AS mol_number, " +
                "molEmployees.nfc AS mol_nfc, " +
                "" +
                "departments.id AS structuralSubdivision_id, " +
                "departments.catalogItemName AS structuralSubdivision_catalogItemName, " +
                "departments.name AS structuralSubdivision_name, " +
                "departments.code AS structuralSubdivision_code, " +
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
                "location.parentId AS locations_parentId " +
                "" +
                "FROM reserves " +
                "LEFT JOIN departments ON reserves.structuralSubdivisionId = departments.id " +
                "LEFT JOIN organizations ON reserves.businessUnitId = organizations.id " +
                "LEFT JOIN location ON reserves.locationId = location.id " +
                "LEFT JOIN employees molEmployees ON reserves.molId = molEmployees.id " +
                "LEFT JOIN nomenclature ON reserves.nomenclatureId = nomenclature.id " +
                "LEFT JOIN nomenclature_group ON reserves.nomenclatureGroupId = nomenclature_group.id " +
                "LEFT JOIN reception_item_category ON reserves.receptionItemCategoryId = reception_item_category.id " +
                "WHERE reserves.id = :id LIMIT 1"
    )
    suspend fun getById(id: String): FullReserve

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(reserves: List<ReserveDb>)
}
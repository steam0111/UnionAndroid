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

    @Query(
        "SELECT reserves.*," +
                "organizations.id AS businessUnit_id, " +
                "organizations.catalogItemName AS business_unit_catalogItemName, " +
                "organizations.name AS business_unit_name, " +
                "organizations.actualAddress AS business_unit_actualAddress, " +
                "organizations.legalAddress AS business_unit_legalAddress, " +
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
                "location.id AS locations_id, " +
                "location.catalogItemName AS locations_catalogItemName, " +
                "location.name AS locations_name, " +
                "location.parentId AS locations_parentId, " +
                "" +
                "departments.id AS structural_subdivision_id, " +
                "departments.catalogItemName AS structural_subdivision_catalogItemName, " +
                "departments.name AS structural_subdivision_name, " +
                "departments.code AS structural_subdivision_code, " +
                "" +
                "nomenclature.id AS nomenclature_id, " +
                "nomenclature.catalogItemName AS nomenclature_catalogItemName, " +
                "nomenclature.name AS nomenclature_name, " +
                "nomenclature.number AS nomenclature_number, " +
                "nomenclature.nomenclatureGroupId AS nomenclature_nomenclatureGroupId, " +
                "" +
                "nomenclature_group.id AS nomenclature_group_id, " +
                "nomenclature_group.catalogItemName AS nomenclature_group_catalogItemName, " +
                "nomenclature_group.name AS nomenclature_group_name, " +
                "" +
                "reception_item_category.id AS reception_item_category_id, " +
                "reception_item_category.name AS reception_item_category_name " +
                "" +
                "FROM reserves " +
                "LEFT JOIN departments ON reserves.structuralSubdivisionId = departments.id " +
                "LEFT JOIN organizations ON reserves.businessUnitId = organizations.id " +
                "LEFT JOIN location ON reserves.locationId = location.id " +
                "LEFT JOIN employees molEmployees ON reserves.molId = molEmployees.id " +
                "LEFT JOIN nomenclature ON reserves.businessUnitId = nomenclature.id " +
                "LEFT JOIN nomenclature_group ON reserves.locationId = nomenclature_group.id " +
                "LEFT JOIN reception_item_category ON reserves.molId = reception_item_category.id " +
                "WHERE reserves.id = :id LIMIT 1"
    )
    suspend fun getById(id: String): FullReserve

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(reserves: List<ReserveDb>)
}
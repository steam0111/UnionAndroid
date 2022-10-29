package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.addNonCancelFilter
import com.example.union_sync_impl.utils.isEquals

fun sqlAccountingObjectDetailQuery(
    rfid: String? = null,
    barcode: String? = null,
    factoryNumber: String? = null,
    isNonCancel: Boolean = true,
): SimpleSQLiteQuery {
    val mainQuery = getMainQuery()

    return SimpleSQLiteQuery(mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "accounting_objects",
            filter = buildList {
                if (isNonCancel) {
                    addNonCancelFilter()
                }
                rfid?.let {
                    add("rfidValue" isEquals rfid)
                }
                barcode?.let {
                    add("barcodeValue" isEquals barcode)
                }
                factoryNumber?.let {
                    add("factoryNumber" isEquals factoryNumber)
                }
            }
        )
    )
    )
}

private fun getMainQuery(): String {
    return "SELECT accounting_objects.*," +
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
            "molEmployees.number AS mol_number, " +
            "molEmployees.nfc AS mol_nfc, " +
            "" +
            "exploitingEmployees.id AS exploiting_id, " +
            "exploitingEmployees.catalogItemName AS exploiting_catalogItemName, " +
            "exploitingEmployees.firstname AS exploiting_firstname, " +
            "exploitingEmployees.lastname AS exploiting_lastname, " +
            "exploitingEmployees.patronymic AS exploiting_patronymic, " +
            "exploitingEmployees.number AS exploiting_number, " +
            "exploitingEmployees.nfc AS exploiting_nfc, " +
            "" +
            "location.id AS locations_id, " +
            "location.catalogItemName AS locations_catalogItemName, " +
            "location.name AS locations_name, " +
            "location.parentId AS locations_parentId, " +
            "" +
            "producer.id AS producer_id, " +
            "producer.catalogItemName AS producer_catalogItemName, " +
            "producer.name AS producer_name, " +
            "producer.code AS producer_code, " +
            "" +
            "equipment_types.id AS equipment_type_id, " +
            "equipment_types.catalogItemName AS equipment_type_catalogItemName, " +
            "equipment_types.name AS equipment_type_name, " +
            "equipment_types.code AS equipment_type_code, " +
            "" +
            "providers.id AS provider_id, " +
            "providers.catalogItemName AS provider_catalogItemName, " +
            "providers.name AS provider_name " +
            "" +
            "FROM accounting_objects " +
            "LEFT JOIN providers ON accounting_objects.producerId = providers.id " +
            "LEFT JOIN equipment_types ON accounting_objects.equipmentTypeId = equipment_types.id " +
            "LEFT JOIN producer ON accounting_objects.producerId = producer.id " +
            "LEFT JOIN location ON accounting_objects.locationId = location.id " +
            "LEFT JOIN employees molEmployees ON accounting_objects.molId = molEmployees.id " +
            "LEFT JOIN structural ON accounting_objects.structuralId = structural.id " +
            "LEFT JOIN employees exploitingEmployees ON accounting_objects.exploitingId = exploitingEmployees.id "
}
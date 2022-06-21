/**
 * OpenAPI definition
 *
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v0
 * 
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package org.openapitools.client.models

import com.squareup.moshi.Json

/**
 * 
 *
 * @param id 
 * @param deleted 
 * @param version 
 * @param dateInsert 
 * @param dateUpdate 
 * @param catalogItemName 
 * @param subName 
 * @param actualPrice 
 * @param startingPrice 
 * @param name 
 * @param model 
 * @param organizationId 
 * @param locationId 
 * @param molId 
 * @param exploitingId 
 * @param producerId 
 * @param typeId 
 * @param providerId 
 * @param accountingObjectStatusId 
 * @param factoryNumber 
 * @param internalNumber 
 * @param invoiceNumber 
 * @param inventoryNumber 
 * @param comment 
 * @param marked 
 * @param accountingObjectCategoryId 
 * @param rfidValue 
 * @param nfcValue 
 * @param barcodeValue 
 * @param departmentId 
 * @param writtenOff 
 * @param oldSystemId 
 * @param regionId 
 * @param branchId 
 * @param commissioningDate 
 * @param arrivalDate 
 * @param writeOffDate 
 * @param category 
 * @param count 
 * @param unit 
 * @param registered 
 * @param commissioned 
 * @param subNumber 
 * @param balanceUnit 
 * @param nomenclatureId 
 * @param nomenclatureGroupId 
 * @param extendedOrganization 
 * @param extendedLocation 
 * @param extendedMol 
 * @param extendedExploiting 
 * @param extendedAccountingObjectStatus 
 * @param extendedProducer 
 * @param extendedProvider 
 * @param extendedType 
 * @param extendedAccountingObjectCategory 
 * @param extendedDepartment 
 * @param extendedRegion 
 * @param extendedBranch 
 * @param extendedNomenclature 
 * @param extendedNomenclatureGroup 
 * @param commissioningId 
 * @param extendedCommissioning 
 * @param actionId 
 * @param extendedAction 
 */

data class AccountingObjectDto (

    @Json(name = "id")
    val id: kotlin.String,

    @Json(name = "deleted")
    val deleted: kotlin.Boolean,

    @Json(name = "version")
    val version: kotlin.Int,

    @Json(name = "dateInsert")
    val dateInsert: kotlin.String? = null,

    @Json(name = "dateUpdate")
    val dateUpdate: kotlin.String? = null,

    @Json(name = "catalogItemName")
    val catalogItemName: kotlin.String? = null,

    @Json(name = "subName")
    val subName: kotlin.String? = null,

    @Json(name = "actualPrice")
    val actualPrice: kotlin.String? = null,

    @Json(name = "startingPrice")
    val startingPrice: kotlin.String? = null,

    @Json(name = "name")
    val name: kotlin.String? = null,

    @Json(name = "model")
    val model: kotlin.String? = null,

    @Json(name = "organizationId")
    val organizationId: kotlin.String? = null,

    @Json(name = "locationId")
    val locationId: kotlin.String? = null,

    @Json(name = "molId")
    val molId: kotlin.String? = null,

    @Json(name = "exploitingId")
    val exploitingId: kotlin.String? = null,

    @Json(name = "producerId")
    val producerId: kotlin.String? = null,

    @Json(name = "typeId")
    val typeId: kotlin.String? = null,

    @Json(name = "providerId")
    val providerId: kotlin.String? = null,

    @Json(name = "accountingObjectStatusId")
    val accountingObjectStatusId: kotlin.String? = null,

    @Json(name = "factoryNumber")
    val factoryNumber: kotlin.String? = null,

    @Json(name = "internalNumber")
    val internalNumber: kotlin.String? = null,

    @Json(name = "invoiceNumber")
    val invoiceNumber: kotlin.String? = null,

    @Json(name = "inventoryNumber")
    val inventoryNumber: kotlin.String? = null,

    @Json(name = "comment")
    val comment: kotlin.String? = null,

    @Json(name = "marked")
    val marked: kotlin.Boolean? = null,

    @Json(name = "accountingObjectCategoryId")
    val accountingObjectCategoryId: kotlin.String? = null,

    @Json(name = "rfidValue")
    val rfidValue: kotlin.String? = null,

    @Json(name = "nfcValue")
    val nfcValue: kotlin.String? = null,

    @Json(name = "barcodeValue")
    val barcodeValue: kotlin.String? = null,

    @Json(name = "departmentId")
    val departmentId: kotlin.String? = null,

    @Json(name = "writtenOff")
    val writtenOff: kotlin.Boolean? = null,

    @Json(name = "oldSystemId")
    val oldSystemId: kotlin.String? = null,

    @Json(name = "regionId")
    val regionId: kotlin.String? = null,

    @Json(name = "branchId")
    val branchId: kotlin.String? = null,

    @Json(name = "commissioningDate")
    val commissioningDate: kotlin.String? = null,

    @Json(name = "arrivalDate")
    val arrivalDate: kotlin.String? = null,

    @Json(name = "writeOffDate")
    val writeOffDate: kotlin.String? = null,

    @Json(name = "category")
    val category: kotlin.String? = null,

    @Json(name = "count")
    val count: java.math.BigDecimal? = null,

    @Json(name = "unit")
    val unit: kotlin.String? = null,

    @Json(name = "registered")
    val registered: kotlin.Boolean? = null,

    @Json(name = "commissioned")
    val commissioned: kotlin.Boolean? = null,

    @Json(name = "subNumber")
    val subNumber: kotlin.String? = null,

    @Json(name = "balanceUnit")
    val balanceUnit: kotlin.String? = null,

    @Json(name = "nomenclatureId")
    val nomenclatureId: kotlin.String? = null,

    @Json(name = "nomenclatureGroupId")
    val nomenclatureGroupId: kotlin.String? = null,

    @Json(name = "extendedOrganization")
    val extendedOrganization: OrganizationDto? = null,

    @Json(name = "extendedLocation")
    val extendedLocation: CustomLocationDto? = null,

    @Json(name = "extendedMol")
    val extendedMol: EmployeeDto? = null,

    @Json(name = "extendedExploiting")
    val extendedExploiting: EmployeeDto? = null,

    @Json(name = "extendedAccountingObjectStatus")
    val extendedAccountingObjectStatus: AccountingObjectStatusDto? = null,

    @Json(name = "extendedProducer")
    val extendedProducer: ProducerDto? = null,

    @Json(name = "extendedProvider")
    val extendedProvider: CounterpartyDto? = null,

    @Json(name = "extendedType")
    val extendedType: EquipmentTypeDto? = null,

    @Json(name = "extendedAccountingObjectCategory")
    val extendedAccountingObjectCategory: AccountingObjectCategoryDto? = null,

    @Json(name = "extendedDepartment")
    val extendedDepartment: DepartmentDto? = null,

    @Json(name = "extendedRegion")
    val extendedRegion: RegionDto? = null,

    @Json(name = "extendedBranch")
    val extendedBranch: BranchDto? = null,

    @Json(name = "extendedNomenclature")
    val extendedNomenclature: NomenclatureDto? = null,

    @Json(name = "extendedNomenclatureGroup")
    val extendedNomenclatureGroup: NomenclatureGroupDto? = null,

    @Json(name = "commissioningId")
    val commissioningId: kotlin.String? = null,

    @Json(name = "extendedCommissioning")
    val extendedCommissioning: CommissioningDto? = null,

    @Json(name = "actionId")
    val actionId: kotlin.String? = null,

    @Json(name = "extendedAction")
    val extendedAction: ActionDto? = null

)


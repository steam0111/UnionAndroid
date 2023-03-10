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

import org.openapitools.client.models.ActionDtoV2
import org.openapitools.client.models.CounterpartyDtoV2
import org.openapitools.client.models.EmployeeDtoV2
import org.openapitools.client.models.EnumDtoV2
import org.openapitools.client.models.EquipmentTypeDtoV2
import org.openapitools.client.models.LocationDtoV2
import org.openapitools.client.models.NomenclatureDtoV2
import org.openapitools.client.models.NomenclatureGroupDtoV2
import org.openapitools.client.models.ProducerDtoV2
import org.openapitools.client.models.ReceptionRecordDtoV2
import org.openapitools.client.models.StructuralUnitDtoV2

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
 * @param code
 * @param userInserted
 * @param userUpdated
 * @param serviceNoteNumberForIssuance
 * @param subName
 * @param actualPrice
 * @param startingPrice
 * @param name
 * @param model
 * @param structuralUnitId
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
 * @param balanceUnitId
 * @param writtenOff
 * @param oldSystemId
 * @param regionId
 * @param commissioningDate
 * @param arrivalDate
 * @param writeOffDate
 * @param category
 * @param count
 * @param unit
 * @param registered
 * @param commissioned
 * @param subNumber
 * @param nomenclatureId
 * @param nomenclatureGroupId
 * @param extendedBalanceUnit
 * @param extendedStructuralUnit
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
 * @param extendedNomenclature
 * @param extendedNomenclatureGroup
 * @param actionId
 * @param extendedAction
 * @param receptionRecordId
 * @param extendedReceptionRecord
 * @param forWriteOff
 * @param tnwed
 * @param traceable
 */

data class AccountingObjectDtoV2(

    @Json(name = "id")
    override val id: kotlin.String,

    @Json(name = "deleted")
    override val deleted: kotlin.Boolean,

    @Json(name = "version")
    val version: kotlin.Int? = null,

    @Json(name = "dateInsert")
    val dateInsert: kotlin.String? = null,

    @Json(name = "dateUpdate")
    val dateUpdate: kotlin.String? = null,

    @Json(name = "catalogItemName")
    val catalogItemName: kotlin.String? = null,

    @Json(name = "code")
    val code: kotlin.String? = null,

    @Json(name = "userInserted")
    val userInserted: kotlin.String? = null,

    @Json(name = "userUpdated")
    val userUpdated: kotlin.String? = null,

    @Json(name = "serviceNoteNumberForIssuance")
    val serviceNoteNumberForIssuance: kotlin.String? = null,

    @Json(name = "subName")
    val subName: kotlin.String? = null,

    @Json(name = "actualPrice")
    val actualPrice: String? = null,

    @Json(name = "startingPrice")
    val startingPrice: String? = null,

    @Json(name = "name")
    val name: kotlin.String? = null,

    @Json(name = "model")
    val model: kotlin.String? = null,

    @Json(name = "structuralUnitId")
    val structuralUnitId: kotlin.String? = null,

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

    @Json(name = "balanceUnitId")
    val balanceUnitId: kotlin.String? = null,

    @Json(name = "writtenOff")
    val writtenOff: kotlin.Boolean? = null,

    @Json(name = "oldSystemId")
    val oldSystemId: kotlin.String? = null,

    @Json(name = "regionId")
    val regionId: kotlin.String? = null,

    @Json(name = "commissioningDate")
    val commissioningDate: kotlin.String? = null,

    @Json(name = "arrivalDate")
    val arrivalDate: kotlin.String? = null,

    @Json(name = "writeOffDate")
    val writeOffDate: kotlin.String? = null,

    @Json(name = "category")
    val category: kotlin.String? = null,

    @Json(name = "count")
    val count: Long? = null,

    @Json(name = "unit")
    val unit: kotlin.String? = null,

    @Json(name = "registered")
    val registered: kotlin.Boolean? = null,

    @Json(name = "commissioned")
    val commissioned: kotlin.Boolean? = null,

    @Json(name = "subNumber")
    val subNumber: kotlin.String? = null,

    @Json(name = "nomenclatureId")
    val nomenclatureId: kotlin.String? = null,

    @Json(name = "nomenclatureGroupId")
    val nomenclatureGroupId: kotlin.String? = null,

    @Json(name = "extendedBalanceUnit")
    val extendedBalanceUnit: StructuralUnitDtoV2? = null,

    @Json(name = "extendedStructuralUnit")
    val extendedStructuralUnit: StructuralUnitDtoV2? = null,

    @Json(name = "extendedLocation")
    val extendedLocation: LocationDtoV2? = null,

    @Json(name = "extendedMol")
    val extendedMol: EmployeeDtoV2? = null,

    @Json(name = "extendedExploiting")
    val extendedExploiting: EmployeeDtoV2? = null,

    @Json(name = "extendedAccountingObjectStatus")
    val extendedAccountingObjectStatus: EnumDtoV2? = null,

    @Json(name = "extendedProducer")
    val extendedProducer: ProducerDtoV2? = null,

    @Json(name = "extendedProvider")
    val extendedProvider: CounterpartyDtoV2? = null,

    @Json(name = "extendedType")
    val extendedType: EquipmentTypeDtoV2? = null,

    @Json(name = "extendedAccountingObjectCategory")
    val extendedAccountingObjectCategory: EnumDtoV2? = null,

    @Json(name = "extendedDepartment")
    val extendedDepartment: StructuralUnitDtoV2? = null,

    @Json(name = "extendedRegion")
    val extendedRegion: StructuralUnitDtoV2? = null,

    @Json(name = "extendedNomenclature")
    val extendedNomenclature: NomenclatureDtoV2? = null,

    @Json(name = "extendedNomenclatureGroup")
    val extendedNomenclatureGroup: NomenclatureGroupDtoV2? = null,

    @Json(name = "actionId")
    val actionId: kotlin.String? = null,

    @Json(name = "extendedAction")
    val extendedAction: ActionDtoV2? = null,

    @Json(name = "receptionRecordId")
    val receptionRecordId: kotlin.String? = null,

    @Json(name = "extendedReceptionRecord")
    val extendedReceptionRecord: ReceptionRecordDtoV2? = null,

    @Json(name = "forWriteOff")
    val forWriteOff: kotlin.Boolean? = null,

    @Json(name = "tnwed")
    val tnwed: kotlin.String? = null,

    @Json(name = "traceable")
    val traceable: kotlin.Boolean? = null,

    @Json(name = "labelTypeId")
    val labelTypeId: String? = null

): DeletedItemDto


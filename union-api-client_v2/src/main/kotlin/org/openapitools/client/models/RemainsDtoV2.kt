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

import org.openapitools.client.models.EmployeeDtoV2
import org.openapitools.client.models.EnumDtoV2
import org.openapitools.client.models.LocationDtoV2
import org.openapitools.client.models.NomenclatureDtoV2
import org.openapitools.client.models.NomenclatureGroupDtoV2
import org.openapitools.client.models.OrderDtoV2
import org.openapitools.client.models.ReceptionRecordDtoV2
import org.openapitools.client.models.StructuralUnitDtoV2

import com.squareup.moshi.Json
import java.math.BigDecimal

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
 * @param name 
 * @param subName 
 * @param nomenclatureId 
 * @param extendedNomenclature 
 * @param molId 
 * @param extendedMol 
 * @param count 
 * @param receptionItemCategoryId 
 * @param extendedReceptionItemCategory 
 * @param nomenclatureGroupId 
 * @param extendedNomenclatureGroup 
 * @param structuralUnitId 
 * @param extendedStructuralUnit 
 * @param locationToId 
 * @param extendedLocation 
 * @param receptionDocumentNumber 
 * @param traceable 
 * @param unitPrice 
 * @param orderId 
 * @param extendedOrder 
 * @param receptionRecordId 
 * @param extendedReceptionRecord 
 * @param tnwed 
 * @param invoiceNumber 
 */

data class RemainsDtoV2 (

    @Json(name = "id")
    val id: kotlin.String,

    @Json(name = "deleted")
    val deleted: kotlin.Boolean,

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

    @Json(name = "name")
    val name: kotlin.String? = null,

    @Json(name = "subName")
    val subName: kotlin.String? = null,

    @Json(name = "nomenclatureId")
    val nomenclatureId: kotlin.String? = null,

    @Json(name = "extendedNomenclature")
    val extendedNomenclature: NomenclatureDtoV2? = null,

    @Json(name = "molId")
    val molId: kotlin.String? = null,

    @Json(name = "extendedMol")
    val extendedMol: EmployeeDtoV2? = null,

    @Json(name = "count")
    val count: Long? = null,

    @Json(name = "receptionItemCategoryId")
    val receptionItemCategoryId: kotlin.String? = null,

    @Json(name = "extendedReceptionItemCategory")
    val extendedReceptionItemCategory: EnumDtoV2? = null,

    @Json(name = "nomenclatureGroupId")
    val nomenclatureGroupId: kotlin.String? = null,

    @Json(name = "extendedNomenclatureGroup")
    val extendedNomenclatureGroup: NomenclatureGroupDtoV2? = null,

    @Json(name = "structuralUnitId")
    val structuralUnitId: kotlin.String? = null,

    @Json(name = "extendedStructuralUnit")
    val extendedStructuralUnit: StructuralUnitDtoV2? = null,

    @Json(name = "locationToId")
    val locationToId: kotlin.String? = null,

    @Json(name = "extendedLocation")
    val extendedLocation: LocationDtoV2? = null,

    @Json(name = "receptionDocumentNumber")
    val receptionDocumentNumber: kotlin.String? = null,

    @Json(name = "traceable")
    val traceable: kotlin.Boolean? = null,

    @Json(name = "unitPrice")
    val unitPrice: kotlin.String? = null,

    @Json(name = "orderId")
    val orderId: kotlin.String? = null,

    @Json(name = "extendedOrder")
    val extendedOrder: OrderDtoV2? = null,

    @Json(name = "receptionRecordId")
    val receptionRecordId: kotlin.String? = null,

    @Json(name = "extendedReceptionRecord")
    val extendedReceptionRecord: ReceptionRecordDtoV2? = null,

    @Json(name = "tnwed")
    val tnwed: kotlin.String? = null,

    @Json(name = "invoiceNumber")
    val invoiceNumber: kotlin.String? = null,

    @Json(name = "barcodeValue")
    val barcodeValue: kotlin.String? = null,

    @Json(name = "labelTypeId")
    val labelTypeId: String? = null,

    @Json(name = "bookkeepingInvoice")
    val bookkeepingInvoice: BigDecimal? = null,

    @Json(name = "consignment")
    val consignment: String? = null
)


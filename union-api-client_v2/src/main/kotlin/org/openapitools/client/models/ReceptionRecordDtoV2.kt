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
 * @param code 
 * @param receptionId 
 * @param extendedReception 
 * @param itemsName 
 * @param itemsSubName 
 * @param nomenclatureId 
 * @param extendedNomenclature 
 * @param businessUnitId 
 * @param extendedBusinessUnit 
 * @param molId 
 * @param extendedMol 
 * @param count 
 * @param receptionItemCategoryId 
 * @param extendedReceptionItemCategory 
 * @param nomenclatureGroupId 
 * @param extendedNomenclatureGroup 
 * @param structuralSubdivisionId 
 * @param extendedStructuralSubdivision 
 * @param locationToId 
 * @param extendedLocation 
 * @param receptionDocumentNumber 
 * @param traceable 
 * @param unitPrice 
 */

data class ReceptionRecordDtoV2 (

    @Json(name = "id")
    val id: kotlin.String,

    @Json(name = "deleted")
    val deleted: kotlin.Boolean,

    @Json(name = "version")
    val version: kotlin.Int?=null,

    @Json(name = "dateInsert")
    val dateInsert: kotlin.String? = null,

    @Json(name = "dateUpdate")
    val dateUpdate: kotlin.String? = null,

    @Json(name = "catalogItemName")
    val catalogItemName: kotlin.String? = null,

    @Json(name = "code")
    val code: kotlin.String? = null,

    @Json(name = "receptionId")
    val receptionId: kotlin.String? = null,

    @Json(name = "extendedReception")
    val extendedReception: ReceptionDtoV2? = null,

    @Json(name = "itemsName")
    val itemsName: kotlin.String? = null,

    @Json(name = "itemsSubName")
    val itemsSubName: kotlin.String? = null,

    @Json(name = "nomenclatureId")
    val nomenclatureId: kotlin.String? = null,

    @Json(name = "extendedNomenclature")
    val extendedNomenclature: NomenclatureDtoV2? = null,

    @Json(name = "businessUnitId")
    val businessUnitId: kotlin.String? = null,

    @Json(name = "extendedBusinessUnit")
    val extendedBusinessUnit: OrganizationDtoV2? = null,

    @Json(name = "molId")
    val molId: kotlin.String? = null,

    @Json(name = "extendedMol")
    val extendedMol: EmployeeDtoV2? = null,

    @Json(name = "count")
    val count: Long? = null,

    @Json(name = "receptionItemCategoryId")
    val receptionItemCategoryId: kotlin.String? = null,

    @Json(name = "extendedReceptionItemCategory")
    val extendedReceptionItemCategory: ReceptionItemCategoryDtoV2? = null,

    @Json(name = "nomenclatureGroupId")
    val nomenclatureGroupId: kotlin.String? = null,

    @Json(name = "extendedNomenclatureGroup")
    val extendedNomenclatureGroup: NomenclatureGroupDtoV2? = null,

    @Json(name = "structuralSubdivisionId")
    val structuralSubdivisionId: kotlin.String? = null,

    @Json(name = "extendedStructuralSubdivision")
    val extendedStructuralSubdivision: DepartmentDtoV2? = null,

    @Json(name = "locationToId")
    val locationToId: kotlin.String? = null,

    @Json(name = "extendedLocation")
    val extendedLocation: LocationDtoV2? = null,

    @Json(name = "receptionDocumentNumber")
    val receptionDocumentNumber: kotlin.String? = null,

    @Json(name = "traceable")
    val traceable: kotlin.Boolean? = null,

    @Json(name = "unitPrice")
    val unitPrice: kotlin.String? = null

)

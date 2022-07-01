package org.openapitools.client.models

import com.squareup.moshi.Json

data class CustomRemainsDto (

    @Json(name = "id")
    val id: String,

    @Json(name = "deleted")
    val deleted: Boolean,

    @Json(name = "version")
    val version: Int? = null,

    @Json(name = "dateInsert")
    val dateInsert: String? = null,

    @Json(name = "dateUpdate")
    val dateUpdate: String? = null,

    @Json(name = "catalogItemName")
    val catalogItemName: String? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "subName")
    val subName: String? = null,

    @Json(name = "nomenclatureId")
    val nomenclatureId: String? = null,

    @Json(name = "extendedNomenclature")
    val extendedNomenclature: CustomNomenclatureDto? = null,

    @Json(name = "businessUnitId")
    val businessUnitId: String? = null,

    @Json(name = "extendedBusinessUnit")
    val extendedBusinessUnit: CustomOrganizationDto? = null,

    @Json(name = "molId")
    val molId: String? = null,

    @Json(name = "extendedMol")
    val extendedMol: CustomEmployeeDto? = null,

    @Json(name = "count")
    val count: java.math.BigDecimal? = null,

    @Json(name = "receptionItemCategoryId")
    val receptionItemCategoryId: String? = null,

    @Json(name = "extendedReceptionItemCategory")
    val extendedReceptionItemCategory: CustomReceptionItemCategoryDto? = null,

    @Json(name = "nomenclatureGroupId")
    val nomenclatureGroupId: String? = null,

    @Json(name = "extendedNomenclatureGroup")
    val extendedNomenclatureGroup: NomenclatureGroupDto? = null,

    @Json(name = "structuralSubdivisionId")
    val structuralSubdivisionId: String? = null,

    @Json(name = "extendedStructuralSubdivision")
    val extendedStructuralSubdivision: CustomDepartmentDto? = null,

    @Json(name = "locationToId")
    val locationToId: String? = null,

    @Json(name = "extendedLocation")
    val extendedLocation: CustomLocationDto? = null,

    @Json(name = "receptionDocumentNumber")
    val receptionDocumentNumber: String? = null,

    @Json(name = "traceable")
    val traceable: Boolean? = null,

    @Json(name = "unitPrice")
    val unitPrice: String? = null,

    @Json(name = "orderId")
    val orderId: String? = null,

    @Json(name = "extendedOrder")
    val extendedOrder: CustomOrderDto? = null

)


package org.openapitools.client.models

import com.squareup.moshi.Json

data class CustomAccountingObjectDto(

    @Json(name = "id")
    val id: String,

    @Json(name = "deleted")
    val deleted: Boolean,

    @Json(name = "version")
    val version: Int?,

    @Json(name = "dateInsert")
    val dateInsert: String? = null,

    @Json(name = "dateUpdate")
    val dateUpdate: String? = null,

    @Json(name = "catalogItemName")
    val catalogItemName: String? = null,

    @Json(name = "subName")
    val subName: String? = null,

    @Json(name = "actualPrice")
    val actualPrice: String? = null,

    @Json(name = "startingPrice")
    val startingPrice: String? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "model")
    val model: String? = null,

    @Json(name = "organizationId")
    val organizationId: String? = null,

    @Json(name = "locationId")
    val locationId: String? = null,

    @Json(name = "molId")
    val molId: String? = null,

    @Json(name = "exploitingId")
    val exploitingId: String? = null,

    @Json(name = "producerId")
    val producerId: String? = null,

    @Json(name = "typeId")
    val typeId: String? = null,

    @Json(name = "providerId")
    val providerId: String? = null,

    @Json(name = "accountingObjectStatusId")
    val accountingObjectStatusId: String? = null,

    @Json(name = "factoryNumber")
    val factoryNumber: String? = null,

    @Json(name = "internalNumber")
    val internalNumber: String? = null,

    @Json(name = "invoiceNumber")
    val invoiceNumber: String? = null,

    @Json(name = "inventoryNumber")
    val inventoryNumber: String? = null,

    @Json(name = "comment")
    val comment: String? = null,

    @Json(name = "marked")
    val marked: Boolean? = null,

    @Json(name = "accountingObjectCategoryId")
    val accountingObjectCategoryId: String? = null,

    @Json(name = "rfidValue")
    val rfidValue: String? = null,

    @Json(name = "nfcValue")
    val nfcValue: String? = null,

    @Json(name = "barcodeValue")
    val barcodeValue: String? = null,

    @Json(name = "departmentId")
    val departmentId: String? = null,

    @Json(name = "writtenOff")
    val writtenOff: Boolean? = null,

    @Json(name = "oldSystemId")
    val oldSystemId: String? = null,

    @Json(name = "regionId")
    val regionId: String? = null,

    @Json(name = "branchId")
    val branchId: String? = null,

    @Json(name = "commissioningDate")
    val commissioningDate: String? = null,

    @Json(name = "arrivalDate")
    val arrivalDate: String? = null,

    @Json(name = "writeOffDate")
    val writeOffDate: String? = null,

    @Json(name = "category")
    val category: String? = null,

    @Json(name = "count")
    val count: java.math.BigDecimal? = null,

    @Json(name = "unit")
    val unit: String? = null,

    @Json(name = "registered")
    val registered: Boolean? = null,

    @Json(name = "commissioned")
    val commissioned: Boolean? = null,

    @Json(name = "subNumber")
    val subNumber: String? = null,

    @Json(name = "balanceUnit")
    val balanceUnit: String? = null,

    @Json(name = "nomenclatureId")
    val nomenclatureId: String? = null,

    @Json(name = "nomenclatureGroupId")
    val nomenclatureGroupId: String? = null,

    @Json(name = "extendedOrganization")
    val extendedOrganization: CustomOrganizationDto? = null,

    @Json(name = "extendedLocation")
    val extendedLocation: CustomLocationDto? = null,

    @Json(name = "extendedMol")
    val extendedMol: CustomEmployeeDto? = null,

    @Json(name = "extendedExploiting")
    val extendedExploiting: CustomEmployeeDto? = null,

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
    val extendedDepartment: CustomDepartmentDto? = null,

    @Json(name = "extendedRegion")
    val extendedRegion: RegionDto? = null,

    @Json(name = "extendedBranch")
    val extendedBranch: BranchDto? = null,

    @Json(name = "extendedNomenclature")
    val extendedNomenclature: CustomNomenclatureDto? = null,

    @Json(name = "extendedNomenclatureGroup")
    val extendedNomenclatureGroup: NomenclatureGroupDto? = null,

    @Json(name = "commissioningId")
    val commissioningId: String? = null,

    @Json(name = "extendedCommissioning")
    val extendedCommissioning: CommissioningDto? = null,

    @Json(name = "actionId")
    val actionId: String? = null,

    @Json(name = "extendedAction")
    val extendedAction: ActionDto? = null

)
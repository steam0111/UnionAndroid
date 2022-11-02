package com.itrocket.union.documents.data.mapper

import com.example.union_sync_api.entity.DocumentSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_api.entity.EnumSyncEntity
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_api.entity.TransitSyncEntity
import com.example.union_sync_api.entity.StructuralSyncEntity
import com.itrocket.union.accountingObjects.data.mapper.map
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.location.data.mapper.toLocationDomain
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.StructuralParamDomain
import com.itrocket.union.reserves.data.mapper.map
import com.itrocket.union.transit.domain.TransitTypeDomain
import com.itrocket.union.structural.data.mapper.toStructuralDomain

fun List<DocumentSyncEntity>.map(): List<DocumentDomain> = map {
    it.map()
}

fun DocumentSyncEntity.map(): DocumentDomain =
    DocumentDomain(
        id = id,
        number = code,
        completionDate = completionDate,
        creationDate = creationDate,
        accountingObjects = accountingObjects.map(),
        reserves = reserves.map(),
        documentStatus = DocumentStatus.valueOf(documentStatus.ifEmpty { DocumentStatus.CREATED.name }),
        documentType = DocumentTypeDomain.valueOf(documentType.ifBlank { DocumentTypeDomain.GIVE.name }),
        params = getParams(
            mol = mol,
            exploiting = exploiting,
            documentType = documentType,
            locationFrom = locationFrom,
            locationTo = locationTo,
            actionBase = actionBase,
            structuralsTo = structuralToSyncEntities,
            structuralsFrom = structuralFromSyncEntities,
            balanceUnitFrom = balanceUnitFrom,
            balanceUnitTo = balanceUnitTo
        ),
        documentStatusId = documentStatusId,
        userInserted = userInserted,
        userUpdated = ""
    )

fun getParams(
    mol: EmployeeSyncEntity? = null,
    exploiting: EmployeeSyncEntity? = null,
    structuralsTo: List<StructuralSyncEntity>? = null,
    structuralsFrom: List<StructuralSyncEntity>? = null,
    documentType: String,
    locationFrom: List<LocationSyncEntity>? = null,
    locationTo: List<LocationSyncEntity>? = null,
    actionBase: EnumSyncEntity? = null,
    balanceUnitFrom: List<StructuralSyncEntity>? = null,
    balanceUnitTo: List<StructuralSyncEntity>? = null
): List<ParamDomain> {
    return getAccountingObjectParams(
        mol = mol,
        exploiting = exploiting,
        structuralsTo = structuralsTo,
        structuralsFrom = structuralsFrom,
        documentType = documentType,
        locationFrom = locationFrom,
        locationTo = locationTo,
        actionBase = actionBase,
        balanceUnitFrom = balanceUnitFrom,
        balanceUnitTo = balanceUnitTo
    )
}

private fun getAccountingObjectParams(
    mol: EmployeeSyncEntity?,
    exploiting: EmployeeSyncEntity?,
    structuralsTo: List<StructuralSyncEntity>?,
    structuralsFrom: List<StructuralSyncEntity>?,
    documentType: String,
    locationFrom: List<LocationSyncEntity>? = null,
    locationTo: List<LocationSyncEntity>? = null,
    actionBase: EnumSyncEntity? = null,
    balanceUnitFrom: List<StructuralSyncEntity>?,
    balanceUnitTo: List<StructuralSyncEntity>?
): List<ParamDomain> {
    val params = mutableListOf<ParamDomain>()
    val type = DocumentTypeDomain.valueOf(documentType)

    addStructuralParam(
        params = params,
        types = type.manualTypes,
        manualType = ManualType.STRUCTURAL_FROM,
        destinations = structuralsFrom
    )
    addStructuralParam(
        params = params,
        types = type.manualTypes,
        manualType = ManualType.BALANCE_UNIT_FROM,
        destinations = balanceUnitFrom,
        isClickable = false
    )
    addLocationParam(
        params = params,
        types = type.manualTypes,
        manualType = ManualType.LOCATION_FROM,
        destinations = locationFrom
    )
    addStructuralParam(
        params = params,
        types = type.manualTypes,
        manualType = ManualType.STRUCTURAL_TO,
        destinations = structuralsTo
    )
    addStructuralParam(
        params = params,
        types = type.manualTypes,
        manualType = ManualType.BALANCE_UNIT_TO,
        destinations = balanceUnitTo,
        isClickable = false
    )
    addLocationParam(
        params = params,
        types = type.manualTypes,
        manualType = ManualType.RELOCATION_LOCATION_TO,
        destinations = locationTo
    )
    addLocationParam(
        params = params,
        types = type.manualTypes,
        manualType = ManualType.LOCATION_TO,
        destinations = locationTo
    )
    addEmployeeParam(
        params = params,
        employee = mol,
        manualType = ManualType.MOL,
        manualTypes = type.manualTypes
    )
    addEmployeeParam(
        params = params,
        employee = mol,
        manualTypes = type.manualTypes,
        manualType = ManualType.MOL_IN_STRUCTURAL
    )
    addEmployeeParam(
        params = params,
        employee = exploiting,
        isFilterExpression = {
            type == DocumentTypeDomain.RETURN
        },
        manualType = ManualType.EXPLOITING,
        manualTypes = type.manualTypes
    )
    addActionBaseParam(
        params = params,
        actionBase = actionBase,
        types = type.manualTypes,
        manualType = ManualType.ACTION_BASE
    )
    return params
}

private fun addStructuralParam(
    params: MutableList<ParamDomain>,
    types: List<ManualType>,
    manualType: ManualType,
    destinations: List<StructuralSyncEntity>?,
    isClickable: Boolean = true
) {
    if (types.contains(manualType)) {
        val structuralParam = StructuralParamDomain(
            manualType = manualType,
            structurals = destinations?.map { it.toStructuralDomain() }.orEmpty(),
            filtered = false,
            clickable = isClickable
        )
        params.add(structuralParam)
    }
}

private fun addActionBaseParam(
    params: MutableList<ParamDomain>,
    actionBase: EnumSyncEntity?,
    types: List<ManualType>,
    manualType: ManualType
) {
    if (!types.contains(manualType)) return
    val departmentValue = actionBase?.name.orEmpty()
    params.add(ParamDomain(actionBase?.id.orEmpty(), departmentValue, manualType))
}

private fun addLocationParam(
    params: MutableList<ParamDomain>,
    types: List<ManualType>,
    manualType: ManualType,
    destinations: List<LocationSyncEntity>?
) {
    if (types.contains(manualType)) {
        val locationParam = LocationParamDomain(
            manualType = manualType,
            locations = destinations?.map { it.toLocationDomain() }.orEmpty(),
            filtered = false
        )
        params.add(locationParam)
    }
}

private fun addEmployeeParam(
    params: MutableList<ParamDomain>,
    employee: EmployeeSyncEntity?,
    manualType: ManualType,
    isFilterExpression: () -> Boolean = { false },
    manualTypes: List<ManualType>
) {
    if (manualTypes.contains(manualType)) {
        val employeeValue = employee?.fullName.orEmpty()
        params.add(
            ParamDomain(
                id = employee?.id.orEmpty(),
                value = employeeValue,
                type = manualType,
                isFilter = isFilterExpression()
            )
        )
    }
}

fun TransitSyncEntity.map(): DocumentDomain = DocumentDomain(
    id = id,
    number = code,
    creationDate = creationDate,
    accountingObjects = accountingObjects.map(),
    reserves = reserves.map(),
    documentStatus = DocumentStatus.valueOf(transitStatus.ifEmpty { DocumentStatus.CREATED.name }),
    documentType = DocumentTypeDomain.TRANSIT,
    params = getTransitParams(
        vehicle = vehicle,
        mol = mol,
        receiving = receiving,
        locationFrom = locationFrom,
        locationTo = locationTo,
        structuralsFrom = structuralFromSyncEntities,
        structuralsTo = structuralToSyncEntities
    ),
    documentStatusId = transitStatusId,
    transitType = if (transitType.isNotBlank()) {
        TransitTypeDomain.valueOf(transitType)
    } else {
        TransitTypeDomain.TRANSIT_SENDING
    },
    userInserted = userInserted,
    userUpdated = userUpdated
)

fun getTransitParams(
    structuralsTo: List<StructuralSyncEntity>? = null,
    structuralsFrom: List<StructuralSyncEntity>? = null,
    vehicle: List<LocationSyncEntity>? = null,
    mol: EmployeeSyncEntity? = null,
    receiving: EmployeeSyncEntity? = null,
    locationFrom: List<LocationSyncEntity>? = null,
    locationTo: List<LocationSyncEntity>? = null,
): List<ParamDomain> {
    val params = mutableListOf<ParamDomain>()
    val type = DocumentTypeDomain.TRANSIT

    addStructuralParam(
        params = params,
        types = type.manualTypes,
        manualType = ManualType.STRUCTURAL_FROM,
        destinations = structuralsFrom
    )
    addStructuralParam(
        params = params,
        types = type.manualTypes,
        manualType = ManualType.STRUCTURAL_TO,
        destinations = structuralsTo
    )
    addEmployeeParam(
        params = params,
        employee = mol,
        manualType = ManualType.MOL,
        manualTypes = type.manualTypes
    )
    addEmployeeParam(
        params = params,
        employee = receiving,
        manualType = ManualType.RECIPIENT,
        manualTypes = type.manualTypes
    )
    addLocationParam(
        params = params,
        types = type.manualTypes,
        manualType = ManualType.LOCATION_TO,
        destinations = locationTo
    )
    addLocationParam(
        params = params,
        types = type.manualTypes,
        manualType = ManualType.TRANSIT,
        destinations = vehicle
    )
    addLocationParam(
        params = params,
        types = type.manualTypes,
        manualType = ManualType.LOCATION_FROM,
        destinations = locationFrom
    )

    return params
}
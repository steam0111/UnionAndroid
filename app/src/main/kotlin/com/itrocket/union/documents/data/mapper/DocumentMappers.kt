package com.itrocket.union.documents.data.mapper

import com.example.union_sync_api.entity.ActionBaseSyncEntity
import com.example.union_sync_api.entity.DocumentSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_api.entity.LocationSyncEntity
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
            structuralTo = structuralToSyncEntity,
            structuralFrom = structuralFromSyncEntity
        ),
        documentStatusId = documentStatusId,
        userInserted = userInserted,
        userUpdated = ""
    )

fun getParams(
    mol: EmployeeSyncEntity? = null,
    exploiting: EmployeeSyncEntity? = null,
    structuralTo: StructuralSyncEntity? = null,
    structuralFrom: StructuralSyncEntity? = null,
    documentType: String,
    locationFrom: LocationSyncEntity? = null,
    locationTo: LocationSyncEntity? = null,
    actionBase: ActionBaseSyncEntity? = null
): List<ParamDomain> {
    return getAccountingObjectParams(
        mol = mol,
        exploiting = exploiting,
        structuralTo = structuralTo,
        structuralFrom = structuralFrom,
        documentType = documentType,
        locationFrom = locationFrom,
        locationTo = locationTo,
        actionBase = actionBase
    )
}

private fun getAccountingObjectParams(
    mol: EmployeeSyncEntity?,
    exploiting: EmployeeSyncEntity?,
    structuralTo: StructuralSyncEntity?,
    structuralFrom: StructuralSyncEntity?,
    documentType: String,
    locationFrom: LocationSyncEntity? = null,
    locationTo: LocationSyncEntity? = null,
    actionBase: ActionBaseSyncEntity? = null
): List<ParamDomain> {
    val params = mutableListOf<ParamDomain>()
    val type = DocumentTypeDomain.valueOf(documentType)

    addStructuralParam(
        params = params,
        types = type.manualTypes,
        manualType = ManualType.STRUCTURAL_FROM,
        destinations = listOfNotNull(structuralFrom)
    )
    addStructuralParam(
        params = params,
        types = type.manualTypes,
        manualType = ManualType.STRUCTURAL_TO,
        destinations = listOfNotNull(structuralTo)
    )
    addMolParam(params = params, mol = mol)
    addExploitingParam(
        params = params,
        exploiting = exploiting,
        documentType = type,
        manualTypes = type.manualTypes
    )
    addLocationParam(
        params = params,
        types = type.manualTypes,
        manualType = ManualType.RELOCATION_LOCATION_TO,
        destinations = listOfNotNull(locationTo)
    )
    addLocationParam(
        params = params,
        types = type.manualTypes,
        manualType = ManualType.LOCATION_TO,
        destinations = listOfNotNull(locationTo)
    )
    addLocationParam(
        params = params,
        types = type.manualTypes,
        manualType = ManualType.LOCATION_FROM,
        destinations = listOfNotNull(locationFrom)
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
) {
    if (types.contains(manualType)) {
        val structuralParam = StructuralParamDomain(
            manualType = manualType,
            structurals = destinations?.map { it.toStructuralDomain() }.orEmpty(),
            filtered = false
        )
        params.add(structuralParam)
    }
}

private fun addMolParam(params: MutableList<ParamDomain>, mol: EmployeeSyncEntity?) {
    val molValue = if (mol != null) {
        "${mol.firstname} ${mol.lastname}"
    } else {
        ""
    }
    params.add(ParamDomain(mol?.id.orEmpty(), molValue, ManualType.MOL))
}

private fun addActionBaseParam(
    params: MutableList<ParamDomain>,
    actionBase: ActionBaseSyncEntity?,
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


private fun addExploitingParam(
    params: MutableList<ParamDomain>,
    exploiting: EmployeeSyncEntity?,
    documentType: DocumentTypeDomain,
    manualTypes: List<ManualType>
) {
    if (manualTypes.contains(ManualType.EXPLOITING)) {
        val exploitingValue = if (exploiting != null) {
            "${exploiting.firstname} ${exploiting.lastname}"
        } else {
            ""
        }
        val param = ParamDomain(
            id = exploiting?.id.orEmpty(),
            value = exploitingValue,
            type = ManualType.EXPLOITING,
            isFilter = documentType == DocumentTypeDomain.RETURN
        )
        params.add(param)
    }
}
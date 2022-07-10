package com.itrocket.union.documents.data.mapper

import com.example.union_sync_api.entity.DocumentSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_api.entity.LocationShortSyncEntity
import com.example.union_sync_api.entity.OrganizationSyncEntity
import com.example.union_sync_api.entity.ReserveSyncEntity
import com.itrocket.union.accountingObjects.data.mapper.map
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.documents.domain.entity.ObjectType
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.reserves.data.mapper.map
import com.itrocket.union.reserves.domain.entity.ReservesDomain

fun List<DocumentSyncEntity>.map(): List<DocumentDomain> = map {
    it.map()
}

fun DocumentSyncEntity.map(): DocumentDomain =
    DocumentDomain(
        number = id,
        completionDate = completionDate,
        creationDate = creationDate,
        accountingObjects = accountingObjects.map(),
        reserves = reserves.map(),
        documentStatus = DocumentStatus.valueOf(documentStatus.ifEmpty { DocumentStatus.CREATED.name }),
        documentType = DocumentTypeDomain.valueOf(documentType.ifBlank { DocumentTypeDomain.EXTRADITION.name }),
        objectType = getObjectType(objectType = objectType, reserves = reserves),
        params = getParams(
            mol = mol,
            exploiting = exploiting,
            organization = organizationSyncEntity,
            documentType = documentType,
            locations = locations,
            objectType = getObjectType(objectType = objectType, reserves = reserves)
        ),
        documentStatusId = documentStatusId,
    )

private fun getObjectType(
    objectType: String?,
    reserves: List<ReserveSyncEntity>,
): ObjectType {
    return when {
        !objectType.isNullOrBlank() -> ObjectType.valueOf(objectType)
        reserves.isNotEmpty() -> ObjectType.RESERVES
        else -> ObjectType.MAIN_ASSETS
    }
}

private fun getParams(
    mol: EmployeeSyncEntity?,
    exploiting: EmployeeSyncEntity?,
    organization: OrganizationSyncEntity?,
    locations: List<LocationShortSyncEntity>?,
    documentType: String,
    objectType: ObjectType
): List<ParamDomain> {
    return when (objectType) {
        ObjectType.MAIN_ASSETS -> getAccountingObjectParams(
            mol = mol,
            exploiting = exploiting,
            organization = organization,
            locations = locations,
            documentType = documentType
        )
        ObjectType.RESERVES -> getReserveParams(
            mol = mol,
            organization = organization,
            locations = locations,
            documentType = documentType
        )
    }
}

private fun getAccountingObjectParams(
    mol: EmployeeSyncEntity?,
    exploiting: EmployeeSyncEntity?,
    organization: OrganizationSyncEntity?,
    locations: List<LocationShortSyncEntity>?,
    documentType: String,
): List<ParamDomain> {
    val params = mutableListOf<ParamDomain>()
    val type = DocumentTypeDomain.valueOf(documentType)

    addOrganizationParam(params = params, organization = organization)
    addMolParam(params = params, mol = mol)
    addExploitingParam(
        params = params,
        exploiting = exploiting,
        documentType = type,
        manualTypes = type.accountingObjectManualTypes
    )
    addLocationParam(
        params = params,
        types = type.accountingObjectManualTypes,
        locations = locations
    )

    return params
}

private fun getReserveParams(
    mol: EmployeeSyncEntity?,
    organization: OrganizationSyncEntity?,
    locations: List<LocationShortSyncEntity>?,
    documentType: String,
): List<ParamDomain> {
    val params = mutableListOf<ParamDomain>()
    val type = DocumentTypeDomain.valueOf(documentType)

    addOrganizationParam(params = params, organization = organization)
    addMolParam(params = params, mol = mol)
    addLocationParam(params = params, types = type.reserveManualTypes, locations = locations)

    return params
}

private fun addOrganizationParam(
    params: MutableList<ParamDomain>,
    organization: OrganizationSyncEntity?
) {
    params.add(
        ParamDomain(
            organization?.id.orEmpty(),
            organization?.name.orEmpty(),
            ManualType.ORGANIZATION
        )
    )
}

private fun addMolParam(params: MutableList<ParamDomain>, mol: EmployeeSyncEntity?) {
    val molValue = if (mol != null) {
        "${mol.firstname} ${mol.lastname}"
    } else {
        ""
    }
    params.add(ParamDomain(mol?.id.orEmpty(), molValue, ManualType.MOL))
}

private fun addLocationParam(
    params: MutableList<ParamDomain>,
    types: List<ManualType>,
    locations: List<LocationShortSyncEntity>?
) {
    if (types.contains(ManualType.LOCATION)) {
        val locationParam = LocationParamDomain(
            ids = locations?.map { it.id }.orEmpty(),
            values = locations?.map { it.name }.orEmpty(),
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
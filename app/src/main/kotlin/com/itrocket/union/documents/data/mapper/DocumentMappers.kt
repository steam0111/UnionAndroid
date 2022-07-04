package com.itrocket.union.documents.data.mapper

import com.example.union_sync_api.entity.DocumentSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_api.entity.LocationShortSyncEntity
import com.example.union_sync_api.entity.OrganizationSyncEntity
import com.itrocket.union.accountingObjects.data.mapper.map
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.documents.domain.entity.ObjectType
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.reserves.data.mapper.map

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
        documentStatus = DocumentStatus.valueOf(documentStatus),
        documentType = DocumentTypeDomain.valueOf(documentType),
        objectType = ObjectType.valueOf(objectType),
        params = getParams(
            mol = mol,
            exploiting = exploiting,
            organization = organizationSyncEntity,
            documentType = documentType,
            locations = locations
        ),
        documentStatusId = documentStatusId,
    )

private fun getParams(
    mol: EmployeeSyncEntity?,
    exploiting: EmployeeSyncEntity?,
    organization: OrganizationSyncEntity?,
    locations: List<LocationShortSyncEntity>?,
    documentType: String
): List<ParamDomain> {
    val params = mutableListOf<ParamDomain>()
    val type = DocumentTypeDomain.valueOf(documentType)
    params.add(
        ParamDomain(
            organization?.id.orEmpty(),
            organization?.name.orEmpty(),
            ManualType.ORGANIZATION
        )
    )
    val molValue = if (mol != null) {
        "${mol.firstname} ${mol.lastname}"
    } else {
        ""
    }
    params.add(ParamDomain(mol?.id.orEmpty(), molValue, ManualType.MOL))

    val exploitingParam = getExploitingParam(
        exploiting = exploiting,
        documentType = type
    )
    if (exploitingParam != null) {
        params.add(exploitingParam)
    }

    val locationParam = getLocationParam(types = type.manualTypes, locations = locations)
    if (locationParam != null) {
        params.add(locationParam)
    }
    return params
}

private fun getLocationParam(
    types: List<ManualType>,
    locations: List<LocationShortSyncEntity>?
): LocationParamDomain? {
    return if (types.contains(ManualType.LOCATION)) {
        LocationParamDomain(
            ids = locations?.map { it.id }.orEmpty(),
            values = locations?.map { it.name }.orEmpty(),
            filtered = false
        )
    } else {
        null
    }
}

private fun getExploitingParam(
    exploiting: EmployeeSyncEntity?,
    documentType: DocumentTypeDomain
): ParamDomain? {
    return if (documentType.manualTypes.contains(ManualType.EXPLOITING)) {
        val exploitingValue = if (exploiting != null) {
            "${exploiting.firstname} ${exploiting.lastname}"
        } else {
            ""
        }
        ParamDomain(
            id = exploiting?.id.orEmpty(),
            value = exploitingValue,
            type = ManualType.EXPLOITING,
            isFilter = documentType == DocumentTypeDomain.RETURN
        )
    } else {
        null
    }
}
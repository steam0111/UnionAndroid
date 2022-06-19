package com.itrocket.union.documents.data.mapper

import com.example.union_sync_api.entity.DocumentSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_api.entity.LocationShortSyncEntity
import com.example.union_sync_api.entity.OrganizationSyncEntity
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.documents.domain.entity.ObjectType
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain

fun List<DocumentSyncEntity>.map(): List<DocumentDomain> = map {
    it.map()
}

fun DocumentSyncEntity.map(): DocumentDomain =
    DocumentDomain(
        number = id,
        date = date,
        accountingObjects = listOf(),
        documentStatus = DocumentStatus.CREATED,
        documentType = DocumentTypeDomain.valueOf(documentType),
        objectType = ObjectType.MAIN_ASSETS,
        params = getParams(
            mol = mol,
            exploiting = exploiting,
            organization = organizationSyncEntity,
            documentType = documentType,
            locations = locations
        )
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

    val exploitingParam = getExploitingParam(type = type.manualType, exploiting = exploiting)
    if (exploitingParam != null) {
        params.add(exploitingParam)
    }

    val locationParam = getLocationParam(type = type.manualType, locations = locations)
    if (locationParam != null) {
        params.add(locationParam)
    }
    return params
}

private fun getLocationParam(
    type: ManualType,
    locations: List<LocationShortSyncEntity>?
): LocationParamDomain? {
    return if (type == ManualType.LOCATION) {
        LocationParamDomain(
            ids = locations?.map { it.id }.orEmpty(),
            values = locations?.map { it.name }.orEmpty()
        )
    } else {
        null
    }
}

private fun getExploitingParam(type: ManualType, exploiting: EmployeeSyncEntity?): ParamDomain? {
    return if (type == ManualType.EXPLOITING) {
        val exploitingValue = if (exploiting != null) {
            "${exploiting.firstname} ${exploiting.lastname}"
        } else {
            ""
        }
        ParamDomain(
            exploiting?.id.orEmpty(),
            exploitingValue,
            ManualType.EXPLOITING
        )
    } else {
        null
    }
}
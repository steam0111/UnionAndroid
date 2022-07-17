package com.itrocket.union.documents.data.mapper

import com.example.union_sync_api.entity.DocumentSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_api.entity.OrganizationSyncEntity
import com.itrocket.union.accountingObjects.data.mapper.map
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.location.data.mapper.toLocationDomain
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
        documentStatus = DocumentStatus.valueOf(documentStatus.ifEmpty { DocumentStatus.CREATED.name }),
        documentType = DocumentTypeDomain.valueOf(documentType.ifBlank { DocumentTypeDomain.GIVE.name }),
        params = getParams(
            mol = mol,
            exploiting = exploiting,
            organization = organizationSyncEntity,
            documentType = documentType,
            locations = locations,
        ),
        documentStatusId = documentStatusId,
    )

fun getParams(
    mol: EmployeeSyncEntity? = null,
    exploiting: EmployeeSyncEntity? = null,
    organization: OrganizationSyncEntity? = null,
    locations: List<LocationSyncEntity>? = null,
    documentType: String,
): List<ParamDomain> {
    return getAccountingObjectParams(
        mol = mol,
        exploiting = exploiting,
        organization = organization,
        locations = locations,
        documentType = documentType
    )
}

private fun getAccountingObjectParams(
    mol: EmployeeSyncEntity?,
    exploiting: EmployeeSyncEntity?,
    organization: OrganizationSyncEntity?,
    locations: List<LocationSyncEntity>?,
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
        manualTypes = type.manualTypes
    )
    addLocationParam(
        params = params,
        types = type.manualTypes,
        locations = locations
    )

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
    locations: List<LocationSyncEntity>?
) {
    if (types.contains(ManualType.LOCATION)) {
        val locationParam = LocationParamDomain(
            locations = locations?.map { it.toLocationDomain() }.orEmpty(),
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
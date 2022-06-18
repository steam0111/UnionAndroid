package com.itrocket.union.documents.data

import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.documents.domain.dependencies.DocumentRepository
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.documents.domain.entity.ObjectType
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.ParamValueDomain

class DocumentRepositoryImpl : DocumentRepository {

    private val mockDocuments = listOf(
        DocumentDomain(
            number = "1234543",
            time = "8:20",
            status = ObjectStatus.AVAILABLE,
            documentStatus = DocumentStatus.CREATED,
            objectType = ObjectType.MAIN_ASSETS,
            documentType = DocumentTypeDomain.WRITE_OFF,
            date = "06.05.2022",
            accountingObjects = listOf(),
            params = listOf(
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = ManualType.ORGANIZATION),
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = ManualType.MOL),
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = DocumentTypeDomain.WRITE_OFF.manualType),
            )
        ),
        DocumentDomain(
            number = "1234543",
            time = "8:20",
            status = ObjectStatus.AVAILABLE,
            date = "05.05.2022",
            documentStatus = DocumentStatus.CREATED,
            documentType = DocumentTypeDomain.WRITE_OFF,
            objectType = ObjectType.MAIN_ASSETS,
            accountingObjects = listOf(),
            params = listOf(
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = ManualType.ORGANIZATION),
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = ManualType.MOL),
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = DocumentTypeDomain.WRITE_OFF.manualType),
            )
        ),
        DocumentDomain(
            number = "1234543",
            time = "8:20",
            status = ObjectStatus.AVAILABLE,
            date = "01.02.2022",
            documentType = DocumentTypeDomain.WRITE_OFF,
            documentStatus = DocumentStatus.CREATED,
            objectType = ObjectType.MAIN_ASSETS,
            accountingObjects = listOf(),
            params = listOf(
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = ManualType.ORGANIZATION),
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = ManualType.MOL),
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = DocumentTypeDomain.WRITE_OFF.manualType),
            )
        ),
        DocumentDomain(
            number = "1234543",
            time = "8:20",
            status = ObjectStatus.AVAILABLE,
            documentStatus = DocumentStatus.CREATED,
            documentType = DocumentTypeDomain.RETURN,
            objectType = ObjectType.MAIN_ASSETS,
            date = "16.12.2021",
            accountingObjects = listOf(),
            params = listOf(
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = ManualType.ORGANIZATION),
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = ManualType.MOL),
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = DocumentTypeDomain.RETURN.manualType),
            )
        ),
        DocumentDomain(
            number = "1234543",
            time = "8:20",
            status = ObjectStatus.AVAILABLE,
            date = "16.12.2021",
            documentType = DocumentTypeDomain.MOVING,
            documentStatus = DocumentStatus.CREATED,
            objectType = ObjectType.MAIN_ASSETS,
            accountingObjects = listOf(),
            params = listOf(
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = ManualType.ORGANIZATION),
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = ManualType.MOL),
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = DocumentTypeDomain.MOVING.manualType),
            )
        ),
        DocumentDomain(
            number = "1234543",
            time = "8:20",
            status = ObjectStatus.AVAILABLE,
            date = "16.12.2021",
            documentType = DocumentTypeDomain.WRITE_OFF,
            documentStatus = DocumentStatus.CREATED,
            objectType = ObjectType.MAIN_ASSETS,
            accountingObjects = listOf(),
            params = listOf(
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = ManualType.ORGANIZATION),
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = ManualType.MOL),
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = DocumentTypeDomain.WRITE_OFF.manualType),
            )
        ),
        DocumentDomain(
            number = "1234543",
            time = "8:20",
            status = ObjectStatus.AVAILABLE,
            date = "16.12.2021",
            documentType = DocumentTypeDomain.RETURN,
            documentStatus = DocumentStatus.CREATED,
            objectType = ObjectType.MAIN_ASSETS,
            accountingObjects = listOf(),
            params = listOf(
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = ManualType.ORGANIZATION),
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = ManualType.MOL),
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = DocumentTypeDomain.RETURN.manualType),
            )
        ),
        DocumentDomain(
            number = "1234543",
            time = "8:20",
            status = ObjectStatus.AVAILABLE,
            date = "16.12.2021",
            documentType = DocumentTypeDomain.EXTRADITION,
            documentStatus = DocumentStatus.CREATED,
            objectType = ObjectType.MAIN_ASSETS,
            accountingObjects = listOf(),
            params = listOf(
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = ManualType.ORGANIZATION),
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = ManualType.MOL),
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = DocumentTypeDomain.EXTRADITION.manualType),
            )
        ),
        DocumentDomain(
            number = "1234543",
            time = "8:20",
            status = ObjectStatus.AVAILABLE,
            date = "16.12.2021",
            documentType = DocumentTypeDomain.COMMISSIONING,
            documentStatus = DocumentStatus.CREATED,
            objectType = ObjectType.MAIN_ASSETS,
            accountingObjects = listOf(),
            params = listOf(
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = ManualType.ORGANIZATION),
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = ManualType.MOL),
                ParamDomain(paramValue = ParamValueDomain("1", "fsdsfsdf"), type = DocumentTypeDomain.COMMISSIONING.manualType),
            )
        )
    )

    override suspend fun getDocuments(type: DocumentTypeDomain): List<DocumentDomain> {
        return if (type == DocumentTypeDomain.ALL) mockDocuments else mockDocuments.filter { it.documentType == type }
    }

}
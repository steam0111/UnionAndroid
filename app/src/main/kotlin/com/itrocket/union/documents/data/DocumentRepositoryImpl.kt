package com.itrocket.union.documents.data

import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.documents.domain.dependencies.DocumentRepository
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.ObjectType

class DocumentRepositoryImpl : DocumentRepository {
    override suspend fun getDocuments(): List<DocumentDomain> {
        return listOf(
            DocumentDomain(
                number = "1234543",
                time = "8:20",
                status = ObjectStatus.AVAILABLE,
                documentStatus = DocumentStatus.CREATED,
                objectType = ObjectType.MAIN_ASSETS,
                date = "06.05.2022",
                documentInfo = listOf(
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                )
            ),
            DocumentDomain(
                number = "1234543",
                time = "8:20",
                status = ObjectStatus.AVAILABLE,
                date = "05.05.2022",
                documentStatus = DocumentStatus.CREATED,
                objectType = ObjectType.MAIN_ASSETS,
                documentInfo = listOf(
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                )
            ),
            DocumentDomain(
                number = "1234543",
                time = "8:20",
                status = ObjectStatus.AVAILABLE,
                date = "01.02.2022",
                documentStatus = DocumentStatus.CREATED,
                objectType = ObjectType.MAIN_ASSETS,
                documentInfo = listOf(
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                )
            ),
            DocumentDomain(
                number = "1234543",
                time = "8:20",
                status = ObjectStatus.AVAILABLE,
                documentStatus = DocumentStatus.CREATED,
                objectType = ObjectType.MAIN_ASSETS,
                date = "16.12.2021",
                documentInfo = listOf(
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                )
            ),
            DocumentDomain(
                number = "1234543",
                time = "8:20",
                status = ObjectStatus.AVAILABLE,
                date = "16.12.2021",
                documentStatus = DocumentStatus.CREATED,
                objectType = ObjectType.MAIN_ASSETS,
                documentInfo = listOf(
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                )
            ),
            DocumentDomain(
                number = "1234543",
                time = "8:20",
                status = ObjectStatus.AVAILABLE,
                date = "16.12.2021",
                documentStatus = DocumentStatus.CREATED,
                objectType = ObjectType.MAIN_ASSETS,
                documentInfo = listOf(
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                )
            ),
            DocumentDomain(
                number = "1234543",
                time = "8:20",
                status = ObjectStatus.AVAILABLE,
                date = "16.12.2021",
                documentStatus = DocumentStatus.CREATED,
                objectType = ObjectType.MAIN_ASSETS,
                documentInfo = listOf(
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                )
            ),
            DocumentDomain(
                number = "1234543",
                time = "8:20",
                status = ObjectStatus.AVAILABLE,
                date = "16.12.2021",
                documentStatus = DocumentStatus.CREATED,
                objectType = ObjectType.MAIN_ASSETS,
                documentInfo = listOf(
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                )
            ),
            DocumentDomain(
                number = "1234543",
                time = "8:20",
                status = ObjectStatus.AVAILABLE,
                date = "16.12.2021",
                documentStatus = DocumentStatus.CREATED,
                objectType = ObjectType.MAIN_ASSETS,
                documentInfo = listOf(
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                )
            )
        )
    }

}
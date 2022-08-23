package com.itrocket.union.documentsMenu.data

import com.itrocket.union.R
import com.itrocket.union.documentsMenu.domain.dependencies.DocumentMenuRepository
import com.itrocket.union.documentsMenu.domain.entity.DocumentMenuDomain
import com.itrocket.union.unionPermissions.domain.entity.UnionPermission

class DocumentMenuRepositoryImpl : DocumentMenuRepository {

    override suspend fun getDocuments(currentDocument: DocumentMenuDomain?): List<DocumentMenuDomain> {
        return when {
            currentDocument?.titleId == R.string.main_property -> {
                listOf(
                    DocumentMenuDomain(
                        titleId = R.string.main_accounting_object,
                        iconId = R.drawable.ic_accounting_object,
                        unionPermission = UnionPermission.ACCOUNTING_OBJECT
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.main_reserves,
                        iconId = R.drawable.ic_reserves,
                        unionPermission = UnionPermission.REMAINS
                    ),
                )
            }
            currentDocument?.titleId == R.string.main_operations -> {
                listOf(
                    DocumentMenuDomain(
                        titleId = R.string.main_issue,
                        iconId = R.drawable.ic_issue,
                        unionPermission = UnionPermission.ALL_DOCUMENTS
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.main_return,
                        iconId = R.drawable.ic_return,
                        unionPermission = UnionPermission.ALL_DOCUMENTS
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.main_moved,
                        iconId = R.drawable.ic_moved,
                        unionPermission = UnionPermission.ALL_DOCUMENTS
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.main_transit,
                        iconId = R.drawable.ic_moved,
                        unionPermission = UnionPermission.ALL_DOCUMENTS
                    )
                    //TODO: Пока попросили скрыть
                    /*DocumentMenuDomain(
                        titleId = R.string.main_write_off,
                        iconId = R.drawable.ic_write_off,
                        unionPermission = UnionPermission.ALL_DOCUMENTS
                    ),*/
                )
            }
            currentDocument?.titleId == R.string.main_books -> {
                listOf(
                    DocumentMenuDomain(
                        titleId = R.string.main_employees,
                        iconId = R.drawable.ic_employees,
                        unionPermission = UnionPermission.EMPLOYEE
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.nomenclature_group,
                        iconId = R.drawable.ic_inventory,
                        unionPermission = UnionPermission.NOMENCLATURE_GROUP
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.nomenclature,
                        iconId = R.drawable.ic_inventory,
                        unionPermission = UnionPermission.NOMENCLATURE
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.producer,
                        iconId = R.drawable.ic_inventory,
                        unionPermission = UnionPermission.PRODUCER
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.counterparties,
                        iconId = R.drawable.ic_inventory,
                        unionPermission = UnionPermission.COUNTERPARTY
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.equipment_types,
                        iconId = R.drawable.ic_inventory,
                        unionPermission = UnionPermission.EQUIPMENT_TYPE
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.manual_structural,
                        iconId = R.drawable.ic_inventory,
                        unionPermission = UnionPermission.STRUCTURAL_UNIT
                    ),
                )
            }
            currentDocument?.titleId == R.string.main_inventory -> {
                listOf(
                    DocumentMenuDomain(
                        titleId = R.string.inventories,
                        iconId = R.drawable.ic_inventory,
                        unionPermission = UnionPermission.INVENTORY
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.create_inventory,
                        iconId = R.drawable.ic_inventory,
                        unionPermission = UnionPermission.INVENTORY
                    ),
                )
            }
            currentDocument == null -> {
                listOf(
                    DocumentMenuDomain(
                        titleId = R.string.main_property,
                        iconId = R.drawable.ic_reserves,
                        unionPermission = UnionPermission.NO_NEED
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.main_operations,
                        iconId = R.drawable.ic_inventory,
                        unionPermission = UnionPermission.NO_NEED
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.main_books,
                        iconId = R.drawable.ic_inventory,
                        unionPermission = UnionPermission.NO_NEED
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.main_inventory,
                        iconId = R.drawable.ic_inventory,
                        unionPermission = UnionPermission.NO_NEED
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.main_identification,
                        iconId = R.drawable.ic_inventory,
                        unionPermission = UnionPermission.NO_NEED
                    ),

                    DocumentMenuDomain(
                        titleId = R.string.sync,
                        iconId = R.drawable.ic_inventory,
                        unionPermission = UnionPermission.NO_NEED
                    ),
                )
            }
            else -> listOf()
        }
    }
}
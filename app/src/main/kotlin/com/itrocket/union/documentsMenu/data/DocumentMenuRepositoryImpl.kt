package com.itrocket.union.documentsMenu.data

import com.itrocket.union.R
import com.itrocket.union.documentsMenu.domain.dependencies.DocumentMenuRepository
import com.itrocket.union.documentsMenu.domain.entity.DocumentMenuDomain

class DocumentMenuRepositoryImpl : DocumentMenuRepository {

    override suspend fun getDocuments(currentDocument: DocumentMenuDomain?): List<DocumentMenuDomain> =
        when {
            currentDocument?.titleId == R.string.main_property -> {
                listOf(
                    DocumentMenuDomain(
                        titleId = R.string.main_accounting_object,
                        iconId = R.drawable.ic_accounting_object
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.main_reserves,
                        iconId = R.drawable.ic_reserves
                    ),
                )
            }
            currentDocument?.titleId == R.string.main_operations -> {
                listOf(
                    DocumentMenuDomain(
                        titleId = R.string.main_commissioning,
                        iconId = R.drawable.ic_commisioning
                    ),
                    DocumentMenuDomain(titleId = R.string.main_issue, iconId = R.drawable.ic_issue),
                    DocumentMenuDomain(
                        titleId = R.string.main_return,
                        iconId = R.drawable.ic_return
                    ),
                    DocumentMenuDomain(titleId = R.string.main_moved, iconId = R.drawable.ic_moved),
                    DocumentMenuDomain(
                        titleId = R.string.main_write_off,
                        iconId = R.drawable.ic_write_off
                    ),
                )
            }
            currentDocument?.titleId == R.string.main_books -> {
                listOf(
                    DocumentMenuDomain(
                        titleId = R.string.main_employees,
                        iconId = R.drawable.ic_employees
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.nomenclature_group,
                        iconId = R.drawable.ic_inventory
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.nomenclature,
                        iconId = R.drawable.ic_inventory
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.organizations,
                        iconId = R.drawable.ic_inventory
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.departments,
                        iconId = R.drawable.ic_inventory
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.producer,
                        iconId = R.drawable.ic_inventory
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.counterparties,
                        iconId = R.drawable.ic_inventory
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.regions,
                        iconId = R.drawable.ic_inventory
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.branches,
                        iconId = R.drawable.ic_inventory
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.equipment_types,
                        iconId = R.drawable.ic_inventory
                    ),
                )
            }
            currentDocument?.titleId == R.string.main_inventory -> {
                listOf(
                    DocumentMenuDomain(
                        titleId = R.string.inventories,
                        iconId = R.drawable.ic_inventory
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.create_inventory,
                        iconId = R.drawable.ic_inventory
                    ),
                )
            }
            currentDocument == null -> {
                listOf(
                    DocumentMenuDomain(
                        titleId = R.string.main_property,
                        iconId = R.drawable.ic_reserves
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.main_operations,
                        iconId = R.drawable.ic_inventory
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.main_books,
                        iconId = R.drawable.ic_inventory
                    ),
                    DocumentMenuDomain(
                        titleId = R.string.main_inventory,
                        iconId = R.drawable.ic_inventory
                    ),
                    DocumentMenuDomain(titleId = R.string.sync, iconId = R.drawable.ic_inventory),
                )
            }
            else -> listOf()
        }
}
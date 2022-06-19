package com.itrocket.union.reserves.data

import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.reserves.domain.dependencies.ReservesRepository
import com.itrocket.union.reserves.domain.entity.ReservesDomain

class ReservesRepositoryImpl : ReservesRepository {
    override suspend fun getReserves(): List<ReservesDomain> {
        return listOf(
            ReservesDomain(
                id = "1", title = "Авторучка «Зебра TR22»", isBarcode = true, listInfo =
                listOf(
                    ObjectInfoDomain(
                        R.string.accounting_objects_factory_num,
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        R.string.accounting_objects_inventory_num,
                        "таылватвлыавыалвыоалвыа"
                    )
                ), itemsCount = 1200
            ),
            ReservesDomain(
                id = "2", title = "Бумага А4 «Русалочка-500 листов»", isBarcode = false, listInfo =
                listOf(
                    ObjectInfoDomain(
                        R.string.manual_location,
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        R.string.manual_exploiting,
                        "таылватвлыавыалвыоалвыа"
                    )
                ), itemsCount = 56
            ),
            ReservesDomain(
                id = "3", title = "Бумага А4 «Русалочка-250 листов»", isBarcode = true, listInfo =
                listOf(
                    ObjectInfoDomain(
                        R.string.manual_location,
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        R.string.accounting_objects_factory_num,
                        "таылватвлыавыалвыоалвыа"
                    )
                ), itemsCount = 167
            ),
            ReservesDomain(
                id = "4", title = "Бумага А4 «Русалочка-150 листов»", isBarcode = true, listInfo =
                listOf(
                    ObjectInfoDomain(
                        R.string.accounting_objects_inventory_num,
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        R.string.accounting_objects_inventory_num,
                        "таылватвлыавыалвыоалвыа"
                    )
                ), itemsCount = 5
            ),
            ReservesDomain(
                id = "5", title = "Авторучка «Зебра TR11»", isBarcode = true, listInfo =
                listOf(
                    ObjectInfoDomain(
                        R.string.accounting_objects_inventory_num,
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        R.string.accounting_objects_inventory_num,
                        "таылватвлыавыалвыоалвыа"
                    )
                ), itemsCount = 200
            ),
            ReservesDomain(
                id = "6", title = "Бумага А5 «Русалочка-1000 листов»", isBarcode = true, listInfo =
                listOf(
                    ObjectInfoDomain(
                        R.string.accounting_objects_inventory_num,
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        R.string.accounting_objects_inventory_num,
                        "таылватвлыавыалвыоалвыа"
                    )
                ), itemsCount = 11
            ),
        )
    }

}
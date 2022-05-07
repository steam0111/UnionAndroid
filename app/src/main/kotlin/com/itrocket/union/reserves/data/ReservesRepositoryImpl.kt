package com.itrocket.union.reserves.data

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
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    )
                ), itemsCount = 1200
            ),
            ReservesDomain(
                id = "2", title = "Бумага А4 «Русалочка-500 листов»", isBarcode = false, listInfo =
                listOf(
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    )
                ), itemsCount = 56
            ),
            ReservesDomain(
                id = "3", title = "Бумага А4 «Русалочка-250 листов»", isBarcode = true, listInfo =
                listOf(
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    )
                ), itemsCount = 167
            ),
            ReservesDomain(
                id = "4", title = "Бумага А4 «Русалочка-150 листов»", isBarcode = true, listInfo =
                listOf(
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    )
                ), itemsCount = 5
            ),
            ReservesDomain(
                id = "5", title = "Авторучка «Зебра TR11»", isBarcode = true, listInfo =
                listOf(
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    )
                ), itemsCount = 200
            ),
            ReservesDomain(
                id = "6", title = "Бумага А5 «Русалочка-1000 листов»", isBarcode = true, listInfo =
                listOf(
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    )
                ), itemsCount = 11
            ),
        )
    }

}
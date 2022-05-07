package com.itrocket.union.di

import com.itrocket.union.accountingObjectDetail.AccountingObjectDetailModule
import com.itrocket.union.accountingObjects.AccountingObjectModule
import com.itrocket.union.core.CoreModule
import com.itrocket.union.documentsMenu.DocumentMenuModule
import com.itrocket.union.filter.FilterModule
import com.itrocket.union.filterValues.FilterValueModule
import com.itrocket.union.readingMode.ReadingModeModule
import com.itrocket.union.reserves.ReservesModule

object Modules {

    val modules = listOf(
        CoreModule.module,
        DocumentMenuModule.module,
        AccountingObjectModule.module,
        AccountingObjectDetailModule.module,
        FilterModule.module,
        FilterValueModule.module,
        AccountingObjectDetailModule.module,
        ReadingModeModule.module,
        ReservesModule.module
    )
}
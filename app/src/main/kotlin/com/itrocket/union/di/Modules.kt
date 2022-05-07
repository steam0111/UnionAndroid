package com.itrocket.union.di

import com.itrocket.union.accountingObjectDetail.AccountingObjectDetailModule
import com.itrocket.union.accountingObjects.AccountingObjectModule
import com.itrocket.union.core.CoreModule
import com.itrocket.union.documentsMenu.DocumentMenuModule
import org.koin.core.module.Module
import com.itrocket.union.readingMode.ReadingModeModule

object Modules {

    val modules = listOf(
        CoreModule.module,
        DocumentMenuModule.module,
        AccountingObjectModule.module,
        AccountingObjectDetailModule.module,
        ReadingModeModule.module
    )
}
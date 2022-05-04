package com.itrocket.union.di

import com.itrocket.union.core.CoreModule
import com.itrocket.union.documentsMenu.DocumentMenuModule
import org.koin.core.module.Module

object Modules {

    val modules = listOf(
        CoreModule.module,
        DocumentMenuModule.module,
    )
}
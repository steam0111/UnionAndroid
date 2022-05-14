package com.itrocket.union.di

import com.itrocket.union.accountingObjectDetail.AccountingObjectDetailModule
import com.itrocket.union.accountingObjects.AccountingObjectModule
import com.itrocket.union.core.CoreModule
import com.itrocket.union.documentsMenu.DocumentMenuModule
import com.itrocket.union.filter.FilterModule
import com.itrocket.union.filterValues.FilterValueModule
import com.itrocket.union.readingMode.ReadingModeModule
import com.itrocket.union.reserves.ReservesModule
import com.itrocket.union.reserveDetail.ReserveDetailModule
import com.itrocket.union.location.LocationModule
import com.itrocket.union.documents.DocumentModule
import com.itrocket.union.authContainer.AuthContainerModule
import com.itrocket.union.authUser.AuthUserModule
import com.itrocket.union.serverConnect.ServerConnectModule
import com.itrocket.union.authMain.AuthMainModule
import com.itrocket.union.network.NetworkModule
import com.itrocket.union.authSelectUser.AuthSelectUserModule

object Modules {

    val modules = listOf(
        CoreModule.module,
        NetworkModule.module,
        DocumentMenuModule.module,
        AccountingObjectModule.module,
        AccountingObjectDetailModule.module,
        FilterModule.module,
        FilterValueModule.module,
        AccountingObjectDetailModule.module,
        ReadingModeModule.module,
        ReservesModule.module,
        ReserveDetailModule.module,
        LocationModule.module,
        ReserveDetailModule.module,
        DocumentModule.module,
        AuthContainerModule.module,
        ServerConnectModule.module,
        AuthUserModule.module,
        AuthMainModule.module,
        AuthSelectUserModule.module
    )
}
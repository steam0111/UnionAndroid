package com.itrocket.union.di

import com.itrocket.union.accountingObjectDetail.AccountingObjectDetailModule
import com.itrocket.union.accountingObjects.AccountingObjectModule
import com.itrocket.union.authContainer.AuthContainerModule
import com.itrocket.union.authMain.AuthMainModule
import com.itrocket.union.authUser.AuthUserModule
import com.itrocket.union.container.ContainerModule
import com.itrocket.union.core.CoreModule
import com.itrocket.union.departmentDetail.DepartmentDetailModule
import com.itrocket.union.departments.DepartmentModule
import com.itrocket.union.documents.DocumentModule
import com.itrocket.union.documentsMenu.DocumentMenuModule
import com.itrocket.union.employeeDetail.EmployeeDetailModule
import com.itrocket.union.employees.EmployeeModule
import com.itrocket.union.filter.FilterModule
import com.itrocket.union.inventories.InventoriesModule
import com.itrocket.union.inventory.InventoryModule
import com.itrocket.union.inventoryCreate.InventoryCreateModule
import com.itrocket.union.location.LocationModule
import com.itrocket.union.moduleSettings.ModuleSettingsModule
import com.itrocket.union.network.NetworkModule
import com.itrocket.union.nomenclature.NomenclatureModule
import com.itrocket.union.nomenclatureDetail.NomenclatureDetailModule
import com.itrocket.union.nomenclatureGroup.NomenclatureGroupModule
import com.itrocket.union.nomenclatureGroupDetail.NomenclatureGroupDetailModule
import com.itrocket.union.organizationDetail.OrganizationDetailModule
import com.itrocket.union.organizations.OrganizationModule
import com.itrocket.union.readingMode.ReadingModeModule
import com.itrocket.union.reserveDetail.ReserveDetailModule
import com.itrocket.union.reserves.ReservesModule
import com.itrocket.union.scanner.ScannerModule
import com.itrocket.union.selectParams.SelectParamsModule
import com.itrocket.union.serverConnect.ServerConnectModule
import com.itrocket.union.switcher.SwitcherModule
import com.itrocket.union.token.TokenModule
import com.union.sdk.SyncModule
import com.itrocket.union.newAccountingObject.NewAccountingObjectModule
import com.itrocket.union.documentCreate.DocumentCreateModule
import com.itrocket.union.producer.ProducerModule
import com.itrocket.union.counterparties.CounterpartyModule
import com.itrocket.union.regions.RegionModule
import com.itrocket.union.branches.BranchesModule
import com.itrocket.union.equipmentTypes.EquipmentTypeModule
import com.itrocket.union.error.ErrorModule
import com.itrocket.union.inventoryContainer.InventoryContainerModule

object Modules {

    val modules = listOf(
        NetworkModule.module,
        CoreModule.module,
        ContainerModule.module,
        DocumentMenuModule.module,
        AccountingObjectModule.module,
        AccountingObjectDetailModule.module,
        FilterModule.module,
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
        NomenclatureGroupModule.module,
        NomenclatureModule.module,
        ModuleSettingsModule.module,
        ScannerModule.module,
        InventoryModule.module,
        SelectParamsModule.module,
        TokenModule.module,
        SyncModule.module,
        InventoryCreateModule.module,
        OrganizationModule.module,
        DepartmentModule.module,
        EmployeeModule.module,
        NewAccountingObjectModule.module,
        SwitcherModule.module,
        InventoriesModule.module,
        RegionModule.module,
        DocumentCreateModule.module,
        BranchesModule.module,
        CounterpartyModule.module,
        ProducerModule.module,
        EquipmentTypeModule.module,
        ErrorModule.module,
        InventoryContainerModule.module,
        ErrorModule.module,
        EmployeeDetailModule.module,
        NomenclatureDetailModule.module,
        DepartmentDetailModule.module,
        NomenclatureGroupDetailModule.module,
        OrganizationDetailModule.module
    )
}
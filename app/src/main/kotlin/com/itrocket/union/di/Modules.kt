package com.itrocket.union.di

import com.itrocket.union.accountingObjectDetail.AccountingObjectDetailModule
import com.itrocket.union.accountingObjects.AccountingObjectModule
import com.itrocket.union.authContainer.AuthContainerModule
import com.itrocket.union.authMain.AuthMainModule
import com.itrocket.union.authUser.AuthUserModule
import com.itrocket.union.branchDetail.BranchDetailModule
import com.itrocket.union.branches.BranchesModule
import com.itrocket.union.chooseAction.ChooseActionModule
import com.itrocket.union.container.ContainerModule
import com.itrocket.union.conterpartyDetail.CounterpartyDetailModule
import com.itrocket.union.core.CoreModule
import com.itrocket.union.counterparties.CounterpartyModule
import com.itrocket.union.departmentDetail.DepartmentDetailModule
import com.itrocket.union.departments.DepartmentModule
import com.itrocket.union.documentCreate.DocumentCreateModule
import com.itrocket.union.documents.DocumentModule
import com.itrocket.union.documentsMenu.DocumentMenuModule
import com.itrocket.union.employeeDetail.EmployeeDetailModule
import com.itrocket.union.employees.EmployeeModule
import com.itrocket.union.equipmentTypeDetail.EquipmentTypeDetailModule
import com.itrocket.union.equipmentTypes.EquipmentTypeModule
import com.itrocket.union.error.ErrorModule
import com.itrocket.union.filter.FilterModule
import com.itrocket.union.identify.IdentifyModule
import com.itrocket.union.inventories.InventoriesModule
import com.itrocket.union.inventory.InventoryModule
import com.itrocket.union.inventoryContainer.InventoryContainerModule
import com.itrocket.union.inventoryCreate.InventoryCreateModule
import com.itrocket.union.location.LocationModule
import com.itrocket.union.moduleSettings.ModuleSettingsModule
import com.itrocket.union.network.NetworkModule
import com.itrocket.union.newAccountingObject.NewAccountingObjectModule
import com.itrocket.union.nomenclature.NomenclatureModule
import com.itrocket.union.nomenclatureDetail.NomenclatureDetailModule
import com.itrocket.union.nomenclatureGroup.NomenclatureGroupModule
import com.itrocket.union.nomenclatureGroupDetail.NomenclatureGroupDetailModule
import com.itrocket.union.organizationDetail.OrganizationDetailModule
import com.itrocket.union.organizations.OrganizationModule
import com.itrocket.union.producer.ProducerModule
import com.itrocket.union.producerDetail.ProducerDetailModule
import com.itrocket.union.readingMode.ReadingModeModule
import com.itrocket.union.regionDetail.RegionDetailModule
import com.itrocket.union.regions.RegionModule
import com.itrocket.union.reserveDetail.ReserveDetailModule
import com.itrocket.union.reserves.ReservesModule
import com.itrocket.union.scanner.ScannerModule
import com.itrocket.union.search.SearchModule
import com.itrocket.union.selectActionWithValuesBottomMenu.SelectActionWithValuesBottomMenuModule
import com.itrocket.union.selectCount.SelectCountModule
import com.itrocket.union.selectParams.SelectParamsModule
import com.itrocket.union.serverConnect.ServerConnectModule
import com.itrocket.union.switcher.SwitcherModule
import com.itrocket.union.syncAll.SyncAllModule
import com.itrocket.union.token.TokenModule
import com.union.sdk.SyncModule

object Modules {

    val modules = listOf(
        AccountingObjectDetailModule.module,
        AccountingObjectModule.module,
        AuthContainerModule.module,
        AuthMainModule.module,
        AuthUserModule.module,
        BranchDetailModule.module,
        BranchesModule.module,
        ChooseActionModule.module,
        ContainerModule.module,
        CoreModule.module,
        CounterpartyDetailModule.module,
        CounterpartyModule.module,
        DepartmentDetailModule.module,
        DepartmentModule.module,
        DocumentCreateModule.module,
        DocumentMenuModule.module,
        DocumentModule.module,
        EmployeeDetailModule.module,
        EmployeeModule.module,
        EquipmentTypeDetailModule.module,
        EquipmentTypeModule.module,
        ErrorModule.module,
        FilterModule.module,
        IdentifyModule.module,
        InventoriesModule.module,
        InventoryContainerModule.module,
        InventoryCreateModule.module,
        InventoryModule.module,
        LocationModule.module,
        ModuleSettingsModule.module,
        NetworkModule.module,
        NewAccountingObjectModule.module,
        NomenclatureDetailModule.module,
        NomenclatureGroupDetailModule.module,
        NomenclatureGroupModule.module,
        NomenclatureModule.module,
        OrganizationDetailModule.module,
        OrganizationModule.module,
        ProducerDetailModule.module,
        ProducerModule.module,
        ReadingModeModule.module,
        RegionDetailModule.module,
        RegionModule.module,
        ReserveDetailModule.module,
        ReservesModule.module,
        ScannerModule.module,
        SearchModule.module,
        SelectActionWithValuesBottomMenuModule.module,
        SelectCountModule.module,
        SelectParamsModule.module,
        ServerConnectModule.module,
        SwitcherModule.module,
        SyncAllModule.module,
        SyncModule.module,
        TokenModule.module,
    )
}
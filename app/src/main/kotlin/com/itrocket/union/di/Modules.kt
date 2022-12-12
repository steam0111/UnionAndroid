package com.itrocket.union.di

import com.itrocket.sgtin.SgtinModule
import com.itrocket.union.accountingObjectDetail.AccountingObjectDetailModule
import com.itrocket.union.accountingObjects.AccountingObjectModule
import com.itrocket.union.authContainer.AuthContainerModule
import com.itrocket.union.authMain.AuthMainModule
import com.itrocket.union.authUser.AuthUserModule
import com.itrocket.union.changeScanData.ChangeScanDataModule
import com.itrocket.union.chooseAction.ChooseActionModule
import com.itrocket.union.comment.CommentModule
import com.itrocket.union.container.ContainerModule
import com.itrocket.union.conterpartyDetail.CounterpartyDetailModule
import com.itrocket.union.core.CoreModule
import com.itrocket.union.counterparties.CounterpartyModule
import com.itrocket.union.dataCollect.DataCollectModule
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
import com.itrocket.union.image.ImageModule
import com.itrocket.union.imageViewer.ImageViewerModule
import com.itrocket.union.inventories.InventoriesModule
import com.itrocket.union.inventory.InventoryModule
import com.itrocket.union.inventoryChoose.InventoryChooseModule
import com.itrocket.union.inventoryContainer.InventoryContainerModule
import com.itrocket.union.inventoryCreate.InventoryCreateModule
import com.itrocket.union.labelType.LabelTypeModule
import com.itrocket.union.labelTypeDetail.LabelTypeDetailModule
import com.itrocket.union.manualInput.ManualInputModule
import com.itrocket.union.moduleSettings.ModuleSettingsModule
import com.itrocket.union.network.NetworkModule
import com.itrocket.union.nfcReader.NfcReaderModule
import com.itrocket.union.nomenclature.NomenclatureModule
import com.itrocket.union.nomenclatureDetail.NomenclatureDetailModule
import com.itrocket.union.nomenclatureGroup.NomenclatureGroupModule
import com.itrocket.union.nomenclatureGroupDetail.NomenclatureGroupDetailModule
import com.itrocket.union.producer.ProducerModule
import com.itrocket.union.producerDetail.ProducerDetailModule
import com.itrocket.union.readerPower.ReaderPowerModule
import com.itrocket.union.readingMode.ReadingModeModule
import com.itrocket.union.reserveDetail.ReserveDetailModule
import com.itrocket.union.reserves.ReservesModule
import com.itrocket.union.scanner.ScannerModule
import com.itrocket.union.search.SearchModule
import com.itrocket.union.selectActionWithValuesBottomMenu.SelectActionWithValuesBottomMenuModule
import com.itrocket.union.selectCount.SelectCountModule
import com.itrocket.union.selectParams.SelectParamsModule
import com.itrocket.union.serverConnect.ServerConnectModule
import com.itrocket.union.splash.SplashModule
import com.itrocket.union.switcher.SwitcherModule
import com.itrocket.union.syncAll.SyncAllModule
import com.itrocket.union.theme.ThemeModule
import com.itrocket.union.token.TokenModule
import com.itrocket.union.transit.TransitModule
import com.itrocket.union.unionPermissions.UnionPermissionsModule
import com.itrocket.union.uniqueDeviceId.UniqueDeviceIdModule
import com.union.sdk.SyncModule

object Modules {

    val modules = listOf(
        ContainerModule.module,
        CoreModule.module,
        ThemeModule.module,
        ProducerModule.module,
        ReadingModeModule.module,
        ReserveDetailModule.module,
        ReservesModule.module,
        ServerConnectModule.module,
        AccountingObjectDetailModule.module,
        AccountingObjectModule.module,
        AuthContainerModule.module,
        AuthMainModule.module,
        AuthUserModule.module,
        NomenclatureGroupModule.module,
        NomenclatureModule.module,
        ModuleSettingsModule.module,
        ScannerModule.module,
        InventoryModule.module,
        SelectParamsModule.module,
        TokenModule.module,
        SyncModule.module,
        InventoryCreateModule.module,
        EmployeeModule.module,
        SwitcherModule.module,
        InventoriesModule.module,
        DocumentCreateModule.module,
        CounterpartyModule.module,
        DocumentCreateModule.module,
        DocumentMenuModule.module,
        DocumentModule.module,
        EmployeeDetailModule.module,
        EmployeeModule.module,
        EquipmentTypeDetailModule.module,
        EquipmentTypeModule.module,
        ErrorModule.module,
        FilterModule.module,
        InventoriesModule.module,
        InventoryContainerModule.module,
        InventoryCreateModule.module,
        InventoryModule.module,
        ModuleSettingsModule.module,
        NetworkModule.module,
        NomenclatureDetailModule.module,
        NomenclatureGroupDetailModule.module,
        SyncAllModule.module,
        CounterpartyDetailModule.module,
        ProducerDetailModule.module,
        EquipmentTypeDetailModule.module,
        SearchModule.module,
        ChooseActionModule.module,
        SelectCountModule.module,
        UnionPermissionsModule.module,
        SelectActionWithValuesBottomMenuModule.module,
        IdentifyModule.module,
        ChangeScanDataModule.module,
        TransitModule.module,
        SplashModule.module,
        NfcReaderModule.module,
        ReaderPowerModule.module,
        ManualInputModule.module,
        DataCollectModule.module,
        InventoryChooseModule.module,
        CommentModule.module,
        ImageViewerModule.module,
        ImageModule.module,
        UniqueDeviceIdModule.module,
        LabelTypeModule.module,
        LabelTypeDetailModule.module,
        SgtinModule.module
    )
}

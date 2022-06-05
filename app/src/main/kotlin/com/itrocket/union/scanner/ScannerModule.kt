package com.itrocket.union.scanner

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.interid.scannerclient_impl.platform.entry.ServiceEntry
import ru.interid.scannerclient_impl.platform.entry.ServiceResponseFlowProvider
import ru.interid.scannerclient_impl.platform.entry.usecase.ApplyReadPower
import ru.interid.scannerclient_impl.platform.entry.usecase.ApplyWritePower
import ru.interid.scannerclient_impl.platform.entry.usecase.ChangeScanMode
import ru.interid.scannerclient_impl.platform.entry.usecase.EpcInventory
import ru.interid.scannerclient_impl.platform.entry.usecase.EpcSearch
import ru.interid.scannerclient_impl.platform.entry.usecase.GetReadPower
import ru.interid.scannerclient_impl.platform.entry.usecase.GetWritePower
import ru.interid.scannerclient_impl.platform.entry.usecase.PrepareBarcodeScan
import ru.interid.scannerclient_impl.platform.entry.usecase.RestartService
import ru.interid.scannerclient_impl.platform.entry.usecase.ServiceUseCases
import ru.interid.scannerclient_impl.platform.entry.usecase.StartBarcodeScan
import ru.interid.scannerclient_impl.platform.entry.usecase.StopBarcodeScan
import ru.interid.scannerclient_impl.platform.entry.usecase.StopRfidScan
import ru.interid.scannerclient_impl.platform.entry.usecase.WriteEpcTag
import ru.interid.scannerclient_impl.platform.entry.usecase.WriteEpcTagByEpcKey
import ru.interid.scannerclient_impl.screen.ServiceEntryManager
import ru.interid.scannerclient_impl.screen.settings.data.PreferenceScannerSettingsDataStore
import ru.interid.scannerclient_impl.screen.settings.data.ScannerSettingsDataSource
import ru.interid.scannerclient_impl.screen.settings.data.ScannerSettingsRepository
import ru.interid.scannerclient_impl.screen.settings.usecase.QueryDefaultService
import ru.interid.scannerclient_impl.screen.settings.usecase.QueryInstalledServices
import ru.interid.scannerclient_impl.screen.settings.usecase.QueryKeyCode
import ru.interid.scannerclient_impl.screen.settings.usecase.RestartServiceAfterSettingsApplied
import ru.interid.scannerclient_impl.screen.settings.usecase.SaveDefaultService
import ru.interid.scannerclient_impl.screen.settings.usecase.SaveKeyCode

object ScannerModule {
    val module = module {
        factory<ScannerSettingsDataSource> {
            PreferenceScannerSettingsDataStore(context = get())
        }
        factory {
            ScannerSettingsRepository(scannerSettingsDataSource = get())
        }
        single {
            ServiceEntry(
                queryInstalledServices = get(),
                queryDefaultService = get(),
                context = get()
            )
        }
        factory {
            ServiceResponseFlowProvider(serviceEntry = get())
        }
        single {
            ServiceEntryManager(serviceUseCases = get())
        }
        factory {
            ServiceUseCases(
                serviceResponseFlowProvider = get(),
                startBarcodeScan = get(),
                stopBarcodeScan = get(),
                restartService = get(),
                applyReadPower = get(),
                applyWritePower = get(),
                changeScanMode = get(),
                epcInventory = get(),
                epcSearch = get(),
                getReadPower = get(),
                getWritePower = get(),
                prepareBarcodeScan = get(),
                queryDefaultService = get(),
                queryInstalledServices = get(),
                queryKeyCode = get(),
                restartServiceAfterSettingsApplied = get(),
                saveDefaultService = get(),
                saveKeyCode = get(),
                stopRfidScan = get(),
                writeEpcTag = get(),
                writeEpcTagByEpcKey = get()
            )
        }
        factory {
            ApplyReadPower(
                serviceEntry = get()
            )
        }
        factory {
            ApplyWritePower(
                serviceEntry = get()
            )
        }
        factory {
            StopRfidScan(
                serviceEntry = get()
            )
        }
        factory {
            WriteEpcTag(
                serviceEntry = get()
            )
        }
        factory {
            WriteEpcTagByEpcKey(
                serviceEntry = get()
            )
        }
        factory {
            ChangeScanMode(
                serviceEntry = get()
            )
        }
        factory {
            EpcInventory(
                serviceEntry = get()
            )
        }
        factory {
            EpcSearch(
                serviceEntry = get()
            )
        }
        factory {
            GetReadPower(
                serviceEntry = get()
            )
        }
        factory {
            GetWritePower(
                serviceEntry = get()
            )
        }
        factory {
            PrepareBarcodeScan(
                serviceEntry = get()
            )
        }
        factory {
            SaveDefaultService(
                scannerSettingsRepository = get()
            )
        }
        factory {
            SaveKeyCode(
                scannerSettingsRepository = get()
            )
        }
        factory {
            QueryKeyCode(
                scannerSettingsRepository = get()
            )
        }
        factory {
            RestartServiceAfterSettingsApplied(
                serviceEntry = get(),
                context = get()
            )
        }
        factory {
            StartBarcodeScan(serviceEntry = get())
        }
        factory {
            StopBarcodeScan(serviceEntry = get())
        }
        factory {
            RestartService(serviceEntry = get())
        }
        factory {
            QueryDefaultService(
                scannerSettingsRepository = get()
            )
        }
        factory {
            QueryInstalledServices(
                scannerSettingsRepository = get()
            )
        }
    }
}
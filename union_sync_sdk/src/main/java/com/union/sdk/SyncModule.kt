package com.union.sdk

import androidx.room.Room
import com.example.union_sync_api.data.*
import com.example.union_sync_impl.UnionDatabase
import com.example.union_sync_impl.data.*
import com.example.union_sync_impl.data.logging.SyncEventsImpl
import com.example.union_sync_impl.sync.SyncInfoRepository
import com.example.union_sync_impl.sync.SyncRepository
import org.koin.dsl.module

object SyncModule {
    val module = module {
        factory<NomenclatureGroupSyncApi> {
            NomenclatureGroupSyncApiImpl(
                nomenclatureGroupDao = get()
            )
        }
        factory<EmployeeSyncApi> {
            EmployeeSyncApiImpl(
                employeeDao = get(),
                structuralSyncApi = get(),
                enumSyncApi = get()
            )
        }
        factory<ProducerSyncApi> {
            ProducerSyncApiImpl(
                producerDao = get()
            )
        }
        factory<AccountingObjectSyncApi> {
            AccountingObjectSyncApiImpl(
                accountingObjectsDao = get(),
                locationSyncApi = get(),
                structuralSyncApi = get(),
                accountingObjectAdditionalFieldsSyncApi = get(),
                enumsApi = get(),
                accountingObjectsCharacteristicSyncApi = get()
            )
        }
        factory<InventorySyncApi> {
            InventorySyncApiImpl(
                inventoryDao = get(),
                locationDao = get(),
                accountingObjectDao = get(),
                inventoryRecordDao = get(),
                locationSyncApi = get(),
                structuralSyncApi = get(),
                checkerSyncApi = get(),
                enumsApi = get(),
                coreDispatchers = get()
            )
        }
        factory<DocumentSyncApi> {
            DocumentSyncApiImpl(
                documentDao = get(),
                accountingObjectDao = get(),
                reserveDao = get(),
                actionRecordDao = get(),
                actionRemainsRecordDao = get(),
                locationSyncApi = get(),
                structuralSyncApi = get(),
                enumsApi = get()
            )
        }
        factory<StructuralSyncApi> {
            StructuralSyncApiImpl(
                structuralDao = get()
            )
        }
        factory<LocationSyncApi> {
            LocationSyncApiImpl(
                locationDao = get(),
            )
        }
        factory<CounterpartySyncApi> {
            CounterpartySyncApiImpl(
                counterpartyDao = get()
            )
        }
        factory<EquipmentTypeSyncApi> {
            EquipmentTypeSyncApiImpl(
                equipmentTypeDao = get()
            )
        }
        factory<ReserveSyncApi> {
            ReserveSyncApiImpl(
                reserveDao = get(),
                structuralSyncApi = get(),
                locationSyncApi = get()
            )
        }
        factory<ReceptionItemCategorySyncApi> {
            ReceptionCategoryItemSyncApiImpl(
                receptionItemCategoryDao = get()
            )
        }
        factory<OrderSyncApi> {
            OrderSyncApiImpl(
                orderDao = get()
            )
        }
        factory<TerminalInfoSyncApi> {
            TerminalInfoSyncApiImpl(terminalInfoDao = get())
        }
        factory<NomenclatureSyncApi> {
            NomenclatureSyncApiImpl(nomenclatureDao = get())
        }
        factory<EnumsSyncApi> {
            EnumsSyncApiImpl(dao = get())
        }
        factory<InventoryRecordSyncApi> {
            InventoryRecordSyncApiImpl(inventoryRecordDao = get())
        }
        factory<ActionRecordSyncApi> {
            ActionRecordSyncApiImpl(actionRecordDao = get())
        }
        factory<ActionRemainsRecordSyncApi> {
            ActionRemainsRecordSyncApiImpl(actionRemainsRecordDao = get())
        }
        factory<LabelTypeSyncApi> {
            LabelTypeSyncApiImpl(labelTypeDao = get())
        }
        factory<TransitSyncApi> {
            TransitSyncApiImpl(
                transitDao = get(),
                accountingObjectDao = get(),
                reserveDao = get(),
                transitAccountingObjectRecordDao = get(),
                transitRemainsRecordDao = get(),
                locationSyncApi = get(),
                structuralSyncApi = get()
            )
        }
        factory<InventoryCheckerSyncApi> {
            InventoryCheckerSyncApiImpl(get())
        }
        single<SyncEventsApi> {
            SyncEventsImpl()
        }
        factory<AllSyncApi> {
            AllSyncImpl(
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get()
            )
        }
        factory<TerminalRemainsNumeratorSyncApi> {
            TerminalRemainsNumeratorSyncApiImpl(terminalRemainsNumeratorDao = get())
        }
        factory<AccountingObjectAdditionalFieldsSyncApi> {
            AccountingObjectAdditionalFieldsSyncApiImpl(
                simpleDao = get(),
                vocabularyDao = get()
            )
        }
        factory<AccountingObjectsCharacteristicSyncApi> {
            AccountingObjectsCharacteristicSyncApiImpl(
                simpleDao = get(),
                vocabularyDao = get()
            )
        }
        single {
            Room.databaseBuilder(
                get(),
                UnionDatabase::class.java, "union_database"
            ).fallbackToDestructiveMigration().build()
        }
        single {
            SyncInfoRepository(
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get()
            )
        }
        factory {
            SyncRepository(
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get()
            )
        }
        factory {
            get<UnionDatabase>().terminalRemainsNumeratorDao()
        }
        factory {
            get<UnionDatabase>().labelTypeDao()
        }
        factory {
            get<UnionDatabase>().nomenclatureGroupDao()
        }
        factory {
            get<UnionDatabase>().accountingObjectUnionImageDao()
        }
        factory {
            get<UnionDatabase>().nomenclatureDao()
        }
        factory {
            get<UnionDatabase>().locationDao()
        }
        factory {
            get<UnionDatabase>().locationPathDao()
        }
        factory {
            get<UnionDatabase>().employeeDao()
        }
        factory {
            get<UnionDatabase>().terminalInfoDao()
        }
        factory {
            get<UnionDatabase>().networkSyncDao()
        }
        factory {
            get<UnionDatabase>().producerDao()
        }
        factory {
            get<UnionDatabase>().counterpartyDao()
        }
        factory {
            get<UnionDatabase>().inventorySyncDao()
        }
        factory {
            get<UnionDatabase>().accountingObjectDao()
        }
        factory {
            get<UnionDatabase>().equipmentTypeDao()
        }
        factory {
            get<UnionDatabase>().documentDao()
        }
        factory {
            get<UnionDatabase>().providerDao()
        }
        factory {
            get<UnionDatabase>().reserveDao()
        }
        factory {
            get<UnionDatabase>().receptionItemCategoryDao()
        }
        factory {
            get<UnionDatabase>().orderDao()
        }
        factory {
            get<UnionDatabase>().actionRecordDao()
        }
        factory {
            get<UnionDatabase>().actionRemainsRecordDao()
        }
        factory {
            get<UnionDatabase>().inventoryRecordDao()
        }
        factory {
            get<UnionDatabase>().structuralDao()
        }
        factory {
            get<UnionDatabase>().structuralPathDao()
        }
        factory {
            get<UnionDatabase>().transitDao()
        }
        factory {
            get<UnionDatabase>().transitAccountingObjectRecordDao()
        }
        factory {
            get<UnionDatabase>().transitRemainsRecordDao()
        }
        factory {
            get<UnionDatabase>().enumsDao()
        }
        factory {
            get<UnionDatabase>().inventoryChecker()
        }
        factory {
            get<UnionDatabase>().accountingObjectSimpleAdditionalFieldDao()
        }
        factory {
            get<UnionDatabase>().accountingObjectVocabularyAdditionalFieldDao()
        }
        factory {
            get<UnionDatabase>().simpleAdditionalFieldDao()
        }
        factory {
            get<UnionDatabase>().vocabularyAdditionalFieldDao()
        }
        factory {
            get<UnionDatabase>().vocabularyAdditionalFieldValueDao()
        }
        factory {
            get<UnionDatabase>().accountingObjectsVocabularyCharacteristicsDao()
        }
        factory {
            get<UnionDatabase>().accountingObjectsSimpleCharacteristicsDao()
        }
        factory {
            get<UnionDatabase>().simpleCharacteristicsDao()
        }
        factory {
            get<UnionDatabase>().vocabularyCharacteristicValueDao()
        }
        factory {
            get<UnionDatabase>().vocabularyCharacteristicsDao()
        }
        factory<ManageSyncDataApi> {
            ManageSyncDataImpl(
                unionDatabase = get(),
                networkSyncDao = get()
            )
        }
    }
}
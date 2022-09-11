package com.union.sdk

import androidx.room.Room
import com.example.union_sync_api.data.AccountingObjectAdditionalFieldsSyncApi
import com.example.union_sync_api.data.AccountingObjectSyncApi
import com.example.union_sync_api.data.ActionRecordSyncApi
import com.example.union_sync_api.data.ActionRemainsRecordSyncApi
import com.example.union_sync_api.data.AllSyncApi
import com.example.union_sync_api.data.CounterpartySyncApi
import com.example.union_sync_api.data.DocumentSyncApi
import com.example.union_sync_api.data.EmployeeSyncApi
import com.example.union_sync_api.data.EnumsSyncApi
import com.example.union_sync_api.data.EquipmentTypeSyncApi
import com.example.union_sync_api.data.InventoryCheckerSyncApi
import com.example.union_sync_api.data.InventoryRecordSyncApi
import com.example.union_sync_api.data.InventorySyncApi
import com.example.union_sync_api.data.LocationSyncApi
import com.example.union_sync_api.data.ManageSyncDataApi
import com.example.union_sync_api.data.NomenclatureGroupSyncApi
import com.example.union_sync_api.data.NomenclatureSyncApi
import com.example.union_sync_api.data.OrderSyncApi
import com.example.union_sync_api.data.ProducerSyncApi
import com.example.union_sync_api.data.ReceptionItemCategorySyncApi
import com.example.union_sync_api.data.ReserveSyncApi
import com.example.union_sync_api.data.StructuralSyncApi
import com.example.union_sync_api.data.TransitSyncApi
import com.example.union_sync_impl.UnionDatabase
import com.example.union_sync_impl.data.AccountingObjectAdditionalFieldsSyncApiImpl
import com.example.union_sync_impl.data.AccountingObjectSyncApiImpl
import com.example.union_sync_impl.data.ActionRecordSyncApiImpl
import com.example.union_sync_impl.data.ActionRemainsRecordSyncApiImpl
import com.example.union_sync_impl.data.AllSyncImpl
import com.example.union_sync_impl.data.CounterpartySyncApiImpl
import com.example.union_sync_impl.data.DocumentSyncApiImpl
import com.example.union_sync_impl.data.EmployeeSyncApiImpl
import com.example.union_sync_impl.data.EnumsSyncApiImpl
import com.example.union_sync_impl.data.EquipmentTypeSyncApiImpl
import com.example.union_sync_impl.data.InventoryCheckerSyncApiImpl
import com.example.union_sync_impl.data.InventoryRecordSyncApiImpl
import com.example.union_sync_impl.data.InventorySyncApiImpl
import com.example.union_sync_impl.data.LocationSyncApiImpl
import com.example.union_sync_impl.data.ManageSyncDataImpl
import com.example.union_sync_impl.data.NomenclatureGroupSyncApiImpl
import com.example.union_sync_impl.data.NomenclatureSyncApiImpl
import com.example.union_sync_impl.data.OrderSyncApiImpl
import com.example.union_sync_impl.data.ProducerSyncApiImpl
import com.example.union_sync_impl.data.ReceptionCategoryItemSyncApiImpl
import com.example.union_sync_impl.data.ReserveSyncApiImpl
import com.example.union_sync_impl.data.StructuralSyncApiImpl
import com.example.union_sync_impl.data.TransitSyncApiImpl
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
                accountingObjectAdditionalFieldsSyncApi = get()
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
                checkerSyncApi = get()
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
                structuralSyncApi = get()
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
        factory<AllSyncApi> {
            AllSyncImpl(
                get(),
                get(),
                get(),
                get()
            )
        }
        factory<AccountingObjectAdditionalFieldsSyncApi> {
            AccountingObjectAdditionalFieldsSyncApiImpl(
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
                get()
            )
        }
        factory {
            get<UnionDatabase>().nomenclatureGroupDao()
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
        factory<ManageSyncDataApi> {
            ManageSyncDataImpl(
                unionDatabase = get(),
                networkSyncDao = get()
            )
        }
    }
}
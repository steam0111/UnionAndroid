package com.union.sdk

import androidx.room.Room
import com.example.union_sync_api.data.AccountingObjectStatusSyncApi
import com.example.union_sync_api.data.AccountingObjectSyncApi
import com.example.union_sync_api.data.AllSyncApi
import com.example.union_sync_api.data.BranchesSyncApi
import com.example.union_sync_api.data.CounterpartySyncApi
import com.example.union_sync_api.data.DepartmentSyncApi
import com.example.union_sync_api.data.DocumentSyncApi
import com.example.union_sync_api.data.EmployeeSyncApi
import com.example.union_sync_api.data.EquipmentTypeSyncApi
import com.example.union_sync_api.data.InventorySyncApi
import com.example.union_sync_api.data.LocationSyncApi
import com.example.union_sync_api.data.ManageSyncDataApi
import com.example.union_sync_api.data.NomenclatureGroupSyncApi
import com.example.union_sync_api.data.NomenclatureSyncApi
import com.example.union_sync_api.data.OrderSyncApi
import com.example.union_sync_api.data.OrganizationSyncApi
import com.example.union_sync_api.data.ProducerSyncApi
import com.example.union_sync_api.data.ReceptionItemCategorySyncApi
import com.example.union_sync_api.data.RegionSyncApi
import com.example.union_sync_api.data.ReserveSyncApi
import com.example.union_sync_impl.UnionDatabase
import com.example.union_sync_impl.data.AccountingObjectStatusSyncApiImpl
import com.example.union_sync_impl.data.AccountingObjectSyncApiImpl
import com.example.union_sync_impl.data.AllSyncImpl
import com.example.union_sync_impl.data.BranchesSyncApiImpl
import com.example.union_sync_impl.data.CounterpartySyncApiImpl
import com.example.union_sync_impl.data.DepartmentSyncApiImpl
import com.example.union_sync_impl.data.DocumentSyncApiImpl
import com.example.union_sync_impl.data.EmployeeSyncApiImpl
import com.example.union_sync_impl.data.EquipmentTypeSyncApiImpl
import com.example.union_sync_impl.data.InventorySyncApiImpl
import com.example.union_sync_impl.data.LocationSyncApiImpl
import com.example.union_sync_impl.data.ManageSyncDataImpl
import com.example.union_sync_impl.data.NomenclatureGroupSyncApiImpl
import com.example.union_sync_impl.data.NomenclatureSyncApiImpl
import com.example.union_sync_impl.data.OrderSyncApiImpl
import com.example.union_sync_impl.data.OrganizationSyncApiImpl
import com.example.union_sync_impl.data.ProducerSyncApiImpl
import com.example.union_sync_impl.data.ReceptionCategoryItemSyncApiImpl
import com.example.union_sync_impl.data.RegionSyncApiImpl
import com.example.union_sync_impl.sync.SyncRepository
import com.example.union_sync_impl.data.ReserveSyncApiImpl
import org.koin.dsl.module
import org.openapitools.client.custom_api.ReceptionItemCategoryApi

object SyncModule {
    val module = module {
        factory<NomenclatureGroupSyncApi> {
            NomenclatureGroupSyncApiImpl(
                nomenclatureGroupDao = get()
            )
        }
        factory<OrganizationSyncApi> {
            OrganizationSyncApiImpl(
                organizationDao = get(),
            )
        }
        factory<DepartmentSyncApi> {
            DepartmentSyncApiImpl(
                departmentDao = get(),
            )
        }
        factory<EmployeeSyncApi> {
            EmployeeSyncApiImpl(
                employeeDao = get(),
            )
        }
        factory<ProducerSyncApi> {
            ProducerSyncApiImpl(
                producerDao = get()
            )
        }
        factory<RegionSyncApi> {
            RegionSyncApiImpl(
                regionDao = get(),
            )
        }
        factory<BranchesSyncApi> {
            BranchesSyncApiImpl(
                branchesDao = get(),
            )
        }
        factory<AccountingObjectSyncApi> {
            AccountingObjectSyncApiImpl(
                accountingObjectsDao = get(),
                locationDao = get(),
            )
        }
        factory<InventorySyncApi> {
            InventorySyncApiImpl(
                inventoryDao = get(),
                locationDao = get(),
                accountingObjectDao = get()
            )
        }
        factory<DocumentSyncApi> {
            DocumentSyncApiImpl(
                documentDao = get(),
                locationDao = get(),
                accountingObjectDao = get(),
                reserveDao = get()
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
                locationDao = get(),
                reserveDao = get()
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
        factory<AccountingObjectStatusSyncApi> {
            AccountingObjectStatusSyncApiImpl(dao = get())
        }
        factory<AllSyncApi> {
            AllSyncImpl(
                get(),
                get(),
                get(),
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
            get<UnionDatabase>().organizationDao()
        }
        factory {
            get<UnionDatabase>().departmentDao()
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
            get<UnionDatabase>().regionDao()
        }
        factory {
            get<UnionDatabase>().branchesDao()
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
            get<UnionDatabase>().statusDao()
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
        factory<ManageSyncDataApi> {
            ManageSyncDataImpl(
                unionDatabase = get(),
                networkSyncDao = get()
            )
        }
    }
}
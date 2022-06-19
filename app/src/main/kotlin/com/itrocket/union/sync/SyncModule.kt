package com.itrocket.union.sync

import androidx.room.Room
import com.example.union_sync_api.data.AccountingObjectSyncApi
import com.example.union_sync_api.data.BranchesSyncApi
import com.example.union_sync_api.data.CounterpartySyncApi
import com.example.union_sync_api.data.DepartmentSyncApi
import com.example.union_sync_api.data.DocumentSyncApi
import com.example.union_sync_api.data.EmployeeSyncApi
import com.example.union_sync_api.data.EquipmentTypesSyncApi
import com.example.union_sync_api.data.InventorySyncApi
import com.example.union_sync_api.data.LocationSyncApi
import com.example.union_sync_api.data.NomenclatureGroupSyncApi
import com.example.union_sync_api.data.OrganizationSyncApi
import com.example.union_sync_api.data.ProducerSyncApi
import com.example.union_sync_api.data.RegionSyncApi
import com.example.union_sync_impl.UnionDatabase
import com.example.union_sync_impl.data.AccountingObjectSyncApiImpl
import com.example.union_sync_impl.data.BranchesSyncApiImpl
import com.example.union_sync_impl.data.CounterpartySyncApiImpl
import com.example.union_sync_impl.data.DepartmentSyncApiImpl
import com.example.union_sync_impl.data.DocumentSyncApiImpl
import com.example.union_sync_impl.data.EmployeeSyncApiImpl
import com.example.union_sync_impl.data.EquipmentTypesSyncApiImpl
import com.example.union_sync_impl.data.InventorySyncApiImpl
import com.example.union_sync_impl.data.LocationSyncApiImpl
import com.example.union_sync_impl.data.NomenclatureGroupSyncApiImpl
import com.example.union_sync_impl.data.OrganizationSyncApiImpl
import com.example.union_sync_impl.data.ProducerSyncApiImpl
import com.example.union_sync_impl.data.RegionSyncApiImpl
import org.koin.dsl.module

object SyncModule {
    val module = module {
        factory<NomenclatureGroupSyncApi> {
            NomenclatureGroupSyncApiImpl(
                nomenclatureGroupApi = get(),
                nomenclatureGroupDao = get()
            )
        }
        factory<OrganizationSyncApi> {
            OrganizationSyncApiImpl(
                organizationApi = get(),
                organizationDao = get(),
                networkSyncDao = get()
            )
        }
        factory<DepartmentSyncApi> {
            DepartmentSyncApiImpl(
                departmentApi = get(),
                departmentDao = get(),
                organizationDao = get()
            )
        }
        factory<EmployeeSyncApi> {
            EmployeeSyncApiImpl(
                employeeApi = get(),
                employeeDao = get(),
                organizationDao = get()
            )
        }
        factory<ProducerSyncApi> {
            ProducerSyncApiImpl(
                producerApi = get(),
                producerDao = get()
            )
        }
        factory<RegionSyncApi> {
            RegionSyncApiImpl(
                regionApi = get(),
                regionDao = get(),
                organizationDao = get()
            )
        }
        factory<BranchesSyncApi> {
            BranchesSyncApiImpl(
                branchesApi = get(),
                branchesDao = get(),
                organizationDao = get()
            )
        }
        factory<AccountingObjectSyncApi> {
            AccountingObjectSyncApiImpl(
                api = get(),
                accountingObjectsDao = get(),
                employeeDao = get(),
                organizationsDao = get(),
                nomenclatureGroupDao = get(),
                nomenclatureDao = get(),
                locationPathDao = get(),
                locationDao = get(),
                departmentDao = get(),
                providerDao = get()
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
                locationDao = get()
            )
        }
        factory<LocationSyncApi> {
            LocationSyncApiImpl(
                locationApi = get(),
                locationDao = get(),
                networkSyncDao = get()
            )
        }
        factory<CounterpartySyncApi> {
            CounterpartySyncApiImpl(
                counterpartyApi = get(),
                counterpartyDao = get()
            )
        }
        factory<EquipmentTypesSyncApi> {
            EquipmentTypesSyncApiImpl(
                equipmentTypeApi = get(),
                equipmentTypeDao = get()
            )
        }

        single {
            Room.databaseBuilder(
                get(),
                UnionDatabase::class.java, "union_database"
            ).fallbackToDestructiveMigration().build()
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
    }
}
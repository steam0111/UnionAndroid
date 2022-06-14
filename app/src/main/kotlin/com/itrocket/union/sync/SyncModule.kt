package com.itrocket.union.sync

import androidx.room.Room
import com.example.union_sync_api.data.DepartmentSyncApi
import com.example.union_sync_api.data.OrganizationSyncApi
import com.example.union_sync_api.data.EmployeeSyncApi
import com.example.union_sync_api.data.NomenclatureGroupSyncApi
import com.example.union_sync_impl.UnionDatabase
import com.example.union_sync_impl.data.DepartmentSyncApiImpl
import com.example.union_sync_impl.data.OrganizationSyncApiImpl
import com.example.union_sync_impl.data.EmployeeSyncApiImpl
import com.example.union_sync_impl.data.NomenclatureGroupSyncApiImpl
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
    }
}
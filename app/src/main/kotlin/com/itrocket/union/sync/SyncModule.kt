package com.itrocket.union.sync

import androidx.room.Room
import com.example.union_sync_api.data.NomenclatureGroupSyncApi
import com.example.union_sync_impl.UnionDatabase
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
    }
}
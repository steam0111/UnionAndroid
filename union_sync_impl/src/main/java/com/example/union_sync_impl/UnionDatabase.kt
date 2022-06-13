package com.example.union_sync_impl

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.LocationPathDao
import com.example.union_sync_impl.dao.NomenclatureDao
import com.example.union_sync_impl.dao.NomenclatureGroupDao
import com.example.union_sync_impl.entity.NomenclatureDb
import com.example.union_sync_impl.entity.NomenclatureGroupDb
import com.example.union_sync_impl.entity.location.LocationDb
import com.example.union_sync_impl.entity.location.LocationPath

@Database(
    entities = [
        NomenclatureGroupDb::class,
        NomenclatureDb::class,
        LocationDb::class,
        LocationPath::class
    ], version = 11
)
abstract class UnionDatabase : RoomDatabase() {
    abstract fun nomenclatureGroupDao(): NomenclatureGroupDao
    abstract fun nomenclatureDao(): NomenclatureDao
    abstract fun locationDao(): LocationDao
    abstract fun locationPathDao(): LocationPathDao
}
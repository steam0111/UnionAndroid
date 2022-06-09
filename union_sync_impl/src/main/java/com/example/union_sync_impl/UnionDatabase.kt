package com.example.union_sync_impl

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.LocationPathDao
import com.example.union_sync_impl.dao.NomenclatureDao
import com.example.union_sync_impl.dao.NomenclatureGroupDao
import com.example.union_sync_impl.entity.Nomenclature
import com.example.union_sync_impl.entity.NomenclatureGroup
import com.example.union_sync_impl.entity.location.Location
import com.example.union_sync_impl.entity.location.LocationPath

@Database(
    entities = [
        NomenclatureGroup::class,
        Nomenclature::class,
        Location::class,
        LocationPath::class
    ], version = 10
)
abstract class UnionDatabase : RoomDatabase() {
    abstract fun nomenclatureGroupDao(): NomenclatureGroupDao
    abstract fun nomenclatureDao(): NomenclatureDao
    abstract fun locationDao(): LocationDao
    abstract fun locationPathDao(): LocationPathDao
}
package com.example.union_sync_impl

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.union_sync_impl.dao.NomenclatureDao
import com.example.union_sync_impl.dao.NomenclatureGroupDao
import com.example.union_sync_impl.entity.Nomenclature
import com.example.union_sync_impl.entity.NomenclatureGroup

@Database(entities = [NomenclatureGroup::class, Nomenclature::class], version = 4)
abstract class UnionDatabase : RoomDatabase() {
    abstract fun nomenclatureGroupDao(): NomenclatureGroupDao
    abstract fun nomenclatureDao(): NomenclatureDao
}
package com.example.union_sync_impl

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.union_sync_impl.dao.NomenclatureGroupDao
import com.example.union_sync_impl.entity.NomenclatureGroup

@Database(entities = [NomenclatureGroup::class], version = 1)
abstract class UnionDatabase : RoomDatabase() {
    abstract fun nomenclatureGroupDao(): NomenclatureGroupDao
}
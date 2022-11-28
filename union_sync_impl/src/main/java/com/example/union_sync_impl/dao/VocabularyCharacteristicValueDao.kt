package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.union_sync_impl.entity.VocabularyCharacteristicValueDb

@Dao
interface VocabularyCharacteristicValueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(checkers: List<VocabularyCharacteristicValueDb>)
}
package com.example.union_sync_impl.entity

import androidx.room.Embedded

class FullAccountingObjectVocabularyCharacteristicDb(
    @Embedded
    val accountingObjectVocabularyCharacteristicDb: AccountingObjectVocabularyCharacteristicsDb,
    @Embedded(prefix = "nameField_")
    val name: VocabularyCharacteristicDb?,
    @Embedded(prefix = "valueField_")
    val value: VocabularyCharacteristicValueDb?,
)
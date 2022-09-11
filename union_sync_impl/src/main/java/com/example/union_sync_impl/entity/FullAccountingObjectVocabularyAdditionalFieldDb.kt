package com.example.union_sync_impl.entity

import androidx.room.Embedded

class FullAccountingObjectVocabularyAdditionalFieldDb(
    @Embedded
    val accountingObjectVocabularyAdditionalFieldDb: AccountingObjectVocabularyAdditionalFieldDb,
    @Embedded(prefix = "nameField_")
    val name: VocabularyAdditionalFieldDb?,
    @Embedded(prefix = "valueField_")
    val value: VocabularyAdditionalFieldValueDb?,
)
package com.example.union_sync_impl.entity

import androidx.room.Embedded

class FullAccountingObjectSimpleAdditionalFieldDb(
    @Embedded
    val accountingObjectSimpleAdditionalFieldDb: AccountingObjectSimpleAdditionalFieldDb,
    @Embedded(prefix = "nameField_")
    val name: SimpleAdditionalFieldDb?,
)
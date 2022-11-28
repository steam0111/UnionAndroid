package com.example.union_sync_impl.entity

import androidx.room.Embedded

class FullAccountingObjectSimpleCharacteristicDb(
    @Embedded
    val accountingObjectSimpleCharacteristicDb: AccountingObjectSimpleCharacteristicsDb,
    @Embedded(prefix = "nameField_")
    val name: SimpleCharacteristicDb?,
)
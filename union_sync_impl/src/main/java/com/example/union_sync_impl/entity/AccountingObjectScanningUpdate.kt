package com.example.union_sync_impl.entity

import androidx.room.Entity

@Entity
class AccountingObjectScanningUpdate(
    val id: String,
    val barcodeValue: String?,
    val rfidValue: String?,
    val factoryNumber: String?,
    val updateDate: Long = System.currentTimeMillis(),
    val marked: Boolean
)
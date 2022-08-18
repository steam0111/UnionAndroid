package com.example.union_sync_api.entity

data class AccountingObjectScanningData(
    val id: String,
    val barcodeValue: String?,
    val rfidValue: String?,
    val factoryNumber: String?
)
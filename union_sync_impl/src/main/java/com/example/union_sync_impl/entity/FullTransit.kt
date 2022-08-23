package com.example.union_sync_impl.entity

import androidx.room.Embedded

class FullTransit(
    @Embedded
    val transitDb: TransitDb,
    @Embedded(prefix = "mol_")
    val molDb: EmployeeDb? = null,
    @Embedded(prefix = "receiving_")
    val receivingDb: EmployeeDb? = null,
)
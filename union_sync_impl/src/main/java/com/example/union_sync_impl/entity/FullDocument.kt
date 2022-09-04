package com.example.union_sync_impl.entity

import androidx.room.Embedded

class FullDocument(
    @Embedded
    val documentDb: DocumentDb,
    @Embedded(prefix = "mol_")
    val molDb: EmployeeDb?,
    @Embedded(prefix = "exploiting_")
    val exploitingDb: EmployeeDb? = null,
    @Embedded(prefix = "action_bases_")
    val actionBaseDb: EnumDb? = null
)
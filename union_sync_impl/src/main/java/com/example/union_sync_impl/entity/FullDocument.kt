package com.example.union_sync_impl.entity

import androidx.room.Embedded
import com.example.union_sync_impl.entity.structural.StructuralDb

class FullDocument(
    @Embedded
    val documentDb: DocumentDb,
    @Embedded(prefix = "mol_")
    val molDb: EmployeeDb?,
    @Embedded(prefix = "exploiting_")
    val exploitingDb: EmployeeDb? = null,
    @Embedded(prefix = "action_bases_")
    val actionBaseDb: ActionBaseDb? = null
)
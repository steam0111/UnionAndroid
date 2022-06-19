package com.example.union_sync_impl.entity

import androidx.room.Embedded

class FullDocument(
    @Embedded
    val documentDb: DocumentDb,
    @Embedded(prefix = "organizations_")
    val organizationDb: OrganizationDb?,
    @Embedded(prefix = "mol_")
    val molDb: EmployeeDb?,
    @Embedded(prefix = "exploiting_")
    val exploitingDb: EmployeeDb? = null
)
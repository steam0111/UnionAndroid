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
    val exploitingDb: EmployeeDb? = null,
    @Embedded(prefix = "departmentFrom_")
    val departmentFromDb: DepartmentDb? = null,
    @Embedded(prefix = "departmentTo_")
    val departmentToDb: DepartmentDb? = null,
    @Embedded(prefix = "branches_")
    val branchDb: BranchesDb? = null,
    @Embedded(prefix = "action_bases_")
    val actionBaseDb: ActionBaseDb? = null
)
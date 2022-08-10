package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.union_sync_impl.entity.core.CatalogItemDb

@Entity(
    foreignKeys = [ForeignKey(
        entity = OrganizationDb::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("organizationId"),
        //onDelete = ForeignKey.CASCADE TODO выставить посже когда появиться больше требования к бд
    )], tableName = "departments"
)
class DepartmentDb(
    id: String,
    override var catalogItemName: String,
    val organizationId: String?,
    val name: String?,
    val code: String?,
    updateDate: Long?
) : CatalogItemDb(id, updateDate)
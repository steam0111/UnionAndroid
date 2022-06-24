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
    )], tableName = "employees"
)
class EmployeeDb(
    id: String,
    override var catalogItemName: String,
    val firstname: String,
    val lastname: String,
    val patronymic: String,
    val organizationId: String?,
    val number: String,
    val nfc: String?,
    val statusId: String?,
    val post: String?
) : CatalogItemDb(id) {

    val fullName: String
        get() = "$lastname $firstname $patronymic"
}
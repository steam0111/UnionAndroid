package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.union_sync_impl.entity.core.CatalogItemDb


@Entity(tableName = "employees")
class EmployeeDb(
    id: String,
    override var catalogItemName: String,
    val firstname: String,
    val lastname: String,
    val patronymic: String,
    val structuralId: String?,
    val number: String,
    val nfc: String?,
    val statusId: String?,
    val post: String?,
    insertDate: Long?,
    updateDate: Long?,
    userInserted: String?,
    userUpdated: String?,
) : CatalogItemDb(
    id = id,
    insertDate = insertDate,
    updateDate = updateDate,
    userUpdated = userUpdated,
    userInserted = userInserted
) {

    val fullName: String
        get() = "$lastname $firstname $patronymic"
}
package com.example.union_sync_api.entity

data class EmployeeSyncEntity(
    val id: String,
    val catalogItemName: String,
    val firstname: String,
    val lastname: String,
    val patronymic: String,
    val structuralId: String?,
    val number: String,
    val nfc: String?,
    val statusId: String?,
    val post: String?,
    val userUpdated: String?,
    var userInserted: String?,
    val dateInsert: Long?,
    var updateDate: Long?
) {
    val fullName: String
        get() = "$lastname $firstname $patronymic"
}
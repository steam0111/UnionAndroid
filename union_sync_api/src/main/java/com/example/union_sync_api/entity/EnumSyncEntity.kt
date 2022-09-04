package com.example.union_sync_api.entity

data class EnumSyncEntity(
    val id: String,
    val name: String,
    val enumType: EnumType
)

enum class EnumType {
    ACCOUNTING_CATEGORY,
    ACCOUNTING_OBJECT_STATUS,
    ACTION_BASE,
    ACTION_STATUS,
    ACTION_TYPE,
    EMPLOYEE_STATUS,
    ENTITY_MODEL_TYPE,
    INVENTORY_BASE,
    INVENTORY_RECORD_STATUS,
    INVENTORY_STATE,
    INVENTORY_TYPE
}
package com.itrocket.union.unionPermissions.domain.entity

enum class UnionPermission(val model: String) {
    ACCOUNTING_OBJECT("accountingObject"),
    REMAINS("remains"),
    EMPLOYEE("employee"),
    NOMENCLATURE_GROUP("nomenclatureGroup"),
    NOMENCLATURE("nomenclature"),
    PRODUCER("producer"),
    COUNTERPARTY("producer"),
    EQUIPMENT_TYPE("equipmentType"),
    INVENTORY("inventory"),
    ALL_DOCUMENTS("action"),
    TRANSIT("transit"),
    STRUCTURAL_UNIT("structuralUnit"),
    NO_NEED("NO_NEED"),

    //Custom Permissions
    OPERATIONS("Operations")
}

/**
 * Метод для маппинга кастомного пермишена, которого нет на сервере в список серверных пермишенов.
 * Это нужно для гибкого использования, как на примере с операциями. Чтобы их отображать в меню
 * нужно иметь хотя бы один из нескольких пермишенов
 * */
fun UnionPermission.customPermissionToListUnionPermissions() = when (this) {
    UnionPermission.OPERATIONS -> listOf(UnionPermission.TRANSIT, UnionPermission.ALL_DOCUMENTS)
    else -> listOf()
}

// actions расположены в порядке уменьшения приоритета, если разрешен DELETE то и другие действия разрешены
enum class Action(val action: String) {
    DELETE("DELETE"),
    WRITE("WRITE"),
    UPDATE("UPDATE"),
    CREATE("CREATE"),
    READ("READ"),
    COMPLETE_WITHOUT_NFC("COMPLETE_WITHOUT_NFC")
}
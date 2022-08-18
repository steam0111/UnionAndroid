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
    STRUCTURAL_UNIT("structuralUnit"),
    NO_NEED("NO_NEED")
}

// actions расположены в порядке уменьшения приоритета, если разрешен DELETE то и другие действия разрешены
enum class Action(val action: String) {
    DELETE("DELETE"),
    WRITE("WRITE"),
    CREATE("CREATE"),
    READ("READ"),
}
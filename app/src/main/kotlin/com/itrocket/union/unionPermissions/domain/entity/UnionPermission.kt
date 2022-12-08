package com.itrocket.union.unionPermissions.domain.entity

enum class UnionPermission(val model: String) {
    ACCOUNTING_OBJECT("accountingObject"),
    REMAINS("remains"),
    EMPLOYEE("employee"),
    LABEL_TYPE("labelType"),
    NOMENCLATURE_GROUP("nomenclatureGroup"),
    NOMENCLATURE("nomenclature"),
    PRODUCER("producer"),
    COUNTERPARTY("counterparty"),
    EQUIPMENT_TYPE("equipmentType"),
    INVENTORY("inventory"),
    ALL_DOCUMENTS("action"),
    TRANSIT("transit"),
    STRUCTURAL_UNIT("structuralUnit"),
    NO_NEED("NO_NEED"),
    ACCOUNTING_OBJECT_SIMPLE_ADDITIONAL_FIELD("accountingObjectSimpleAdditionalFieldValue"),
    ACCOUNTING_OBJECT_VOCABULARY_ADDITIONAL_FIELD("accountingObjectVocabularyAdditionalFieldValue"),

    //Custom Permissions
    OPERATIONS("Operations"),
    REFERENCES("References"),
    PROPERTY("Property"),
}

/**
 * Метод для маппинга кастомного пермишена, которого нет на сервере в список серверных пермишенов.
 * Это нужно для гибкого использования, как на примере с операциями. Чтобы их отображать в меню
 * нужно иметь хотя бы один из нескольких пермишенов
 * */
fun UnionPermission.customPermissionToListUnionPermissions() = when (this) {
    UnionPermission.OPERATIONS -> listOf(UnionPermission.TRANSIT, UnionPermission.ALL_DOCUMENTS)
    UnionPermission.REFERENCES -> listOf(
        UnionPermission.EMPLOYEE,
        UnionPermission.NOMENCLATURE_GROUP,
        UnionPermission.NOMENCLATURE,
        UnionPermission.PRODUCER,
        UnionPermission.COUNTERPARTY,
        UnionPermission.EQUIPMENT_TYPE,
        UnionPermission.STRUCTURAL_UNIT,
        UnionPermission.LABEL_TYPE,
    )
    UnionPermission.PROPERTY -> listOf(UnionPermission.ACCOUNTING_OBJECT, UnionPermission.REMAINS)
    else -> listOf()
}

// actions расположены в порядке уменьшения приоритета, если разрешен DELETE то и другие действия разрешены
enum class Action(val action: String) {
    DELETE("DELETE"),
    UPDATE("UPDATE"),
    CREATE("CREATE"),
    READ("READ"),
    COMPLETE_WITHOUT_NFC("COMPLETE_WITHOUT_NFC"),
    COMPLETE_INVENTORY("COMPLETE")
}
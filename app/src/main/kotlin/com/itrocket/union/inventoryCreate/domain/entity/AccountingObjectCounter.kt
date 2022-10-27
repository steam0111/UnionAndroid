package com.itrocket.union.inventoryCreate.domain.entity

data class AccountingObjectCounter(
    val total: Int = 0, // общее количество ОУ со статусами "найден" и "не найден"
    val found: Int = 0, // количество ОУ со статусом "найден";
    val notFound: Int = 0, // количество ОУ со статусом "найден";
    val new: Int = 0, // количество ОУ со статусом "новый".
)
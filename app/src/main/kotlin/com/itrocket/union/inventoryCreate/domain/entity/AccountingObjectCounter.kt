package com.itrocket.union.inventoryCreate.domain.entity

data class AccountingObjectCounter(
    val total: Long = 0L, // общее количество ОУ со статусами "найден" и "не найден"
    val found: Long = 0L, // количество ОУ со статусом "найден";
    val notFound: Long = 0L, // количество ОУ со статусом "найден";
    val new: Long = 0L, // количество ОУ со статусом "новый".
)
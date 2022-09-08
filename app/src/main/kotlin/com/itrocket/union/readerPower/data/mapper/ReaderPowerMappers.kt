package com.itrocket.union.readerPower.data.mapper

import com.itrocket.union.readerPower.domain.entity.ReaderPowerDomain

fun List<Any>.map(): List<ReaderPowerDomain> = map {
    ReaderPowerDomain()
}
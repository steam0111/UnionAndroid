package com.itrocket.union.identify.domain

data class NomenclatureReserveRfid(
    val newNomenclatureReserves: List<NomenclatureReserveDomain>,
    val newRfids: List<String>
)
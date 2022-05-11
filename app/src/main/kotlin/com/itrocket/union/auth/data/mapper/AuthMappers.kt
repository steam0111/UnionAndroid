package com.itrocket.union.auth.data.mapper

import com.itrocket.union.auth.domain.entity.AuthDomain

fun List<Any>.map(): List<AuthDomain> = map {
    AuthDomain()
}
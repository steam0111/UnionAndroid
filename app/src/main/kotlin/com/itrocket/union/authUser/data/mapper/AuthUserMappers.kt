package com.itrocket.union.authUser.data.mapper

import com.itrocket.union.authUser.domain.entity.AuthUserDomain

fun List<Any>.map(): List<AuthUserDomain> = map {
    AuthUserDomain()
}
package com.itrocket.union.authSelectUser.data.mapper

import com.itrocket.union.authSelectUser.domain.entity.AuthSelectUserDomain

fun List<Any>.map(): List<AuthSelectUserDomain> = map {
    AuthSelectUserDomain()
}
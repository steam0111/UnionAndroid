package com.itrocket.union.authAndLicense.data.mapper

import com.itrocket.union.authAndLicense.domain.entity.AuthAndLicenseDomain

fun List<Any>.map(): List<AuthAndLicenseDomain> = map {
    AuthAndLicenseDomain()
}
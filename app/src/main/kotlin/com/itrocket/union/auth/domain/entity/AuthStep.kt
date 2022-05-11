package com.itrocket.union.auth.domain.entity

enum class AuthStep(
    val stepNumber: Int,
) {
    AUTH_AND_LICENSE(
        stepNumber = 1
    ),
    CONNECT_TO_SERVER(
        stepNumber = 2
    ),
    AUTH_USER(
        stepNumber = 3
    )
}
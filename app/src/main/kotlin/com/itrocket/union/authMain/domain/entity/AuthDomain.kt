package com.itrocket.union.authMain.domain.entity

data class AuthDomain(
    val accessToken: String,
    val refreshToken: String
)
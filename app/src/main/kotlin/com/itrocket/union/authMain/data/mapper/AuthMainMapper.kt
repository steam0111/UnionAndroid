package com.itrocket.union.authMain.data.mapper

import com.itrocket.union.authMain.data.AuthRequest
import com.itrocket.union.authMain.domain.entity.AuthCredsDomain

fun AuthCredsDomain.toAuthRequest() = AuthRequest(
    login = login,
    password = password
)
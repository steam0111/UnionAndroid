package com.itrocket.union.authMain.data.mapper

import com.itrocket.union.authMain.domain.entity.AuthCredsDomain
import com.itrocket.union.authMain.domain.entity.AuthDomain
import com.itrocket.union.network.InvalidNetworkDataException
import org.openapitools.client.models.AuthJwtRequest
import org.openapitools.client.models.AuthJwtResponse

fun AuthCredsDomain.toAuthJwtRequest() = AuthJwtRequest(
    login = login,
    password = password
)

fun AuthJwtResponse.toAuthDomain() = AuthDomain(
    accessToken = accessToken ?: throw InvalidNetworkDataException(),
    refreshToken = refreshToken ?: throw InvalidNetworkDataException()
)
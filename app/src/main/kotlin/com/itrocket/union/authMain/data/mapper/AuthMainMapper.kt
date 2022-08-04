package com.itrocket.union.authMain.data.mapper

import com.itrocket.token_auth.AuthCredentials
import com.itrocket.union.authMain.domain.entity.AuthCredsDomain
import com.itrocket.union.authMain.domain.entity.AuthDomain
import com.itrocket.union.authMain.domain.entity.MyConfigDomain
import com.itrocket.union.network.InvalidNetworkDataException
import org.openapitools.client.models.AuthJwtRequest
import org.openapitools.client.models.AuthJwtResponse
import org.openapitools.client.models.GetMyPermissionsResponseV2
import org.openapitools.client.models.RefreshJwtResponse

fun AuthCredsDomain.toAuthJwtRequest() = AuthJwtRequest(
    login = login,
    password = password
)

fun AuthJwtResponse.toAuthDomain() = AuthDomain(
    accessToken = accessToken ?: throw InvalidNetworkDataException(),
    refreshToken = refreshToken ?: throw InvalidNetworkDataException()
)

fun AuthCredentials.toAuthDomain() = AuthDomain(
    accessToken = accessToken,
    refreshToken = refreshToken
)

fun RefreshJwtResponse.toAuthCredentials() = AuthCredentials(
    accessToken = accessToken ?: throw InvalidNetworkDataException(),
    refreshToken = refreshToken ?: throw InvalidNetworkDataException()
)

fun GetMyPermissionsResponseV2?.toMyConfigDomain() = MyConfigDomain(
    organizationId = this?.employee?.organizationId.orEmpty(),
    employeeId = this?.employee?.id.orEmpty()
)
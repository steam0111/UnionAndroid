package com.itrocket.token_auth

import okhttp3.Request


internal fun Request.Builder.addAuthHeader(accessToken: String):Request.Builder {
    return addHeader(
        Const.TOKEN_HEADER_NAME,
        "${Const.TOKEN_HEADER_PREFIX} $accessToken"
    )
}

internal fun Request.Builder.removeAuthHeader(): Request.Builder {
    return removeHeader(Const.TOKEN_HEADER_NAME)
}
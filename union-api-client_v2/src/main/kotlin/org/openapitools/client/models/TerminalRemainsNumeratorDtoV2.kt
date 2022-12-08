package org.openapitools.client.models

import com.squareup.moshi.Json

data class TerminalRemainsNumeratorDtoV2(
    @Json(name = "terminalPrefix")
    val terminalPrefix: Int,

    @Json(name = "terminalId")
    val terminalId: String,

    @Json(name = "remainsId")
    val remainsId: String,

    @Json(name = "extendedRemains")
    val extendedRemains: RemainsDtoV2? = null,

    @Json(name = "actualNumber")
    val actualNumber: Int,

    @Json(name = "deleted")
    val deleted: kotlin.Boolean,

    @Json(name = "version")
    val version: kotlin.Int? = null,

    @Json(name = "dateInsert")
    val dateInsert: kotlin.String? = null,

    @Json(name = "dateUpdate")
    val dateUpdate: kotlin.String? = null,

    @Json(name = "userInserted")
    val userInserted: kotlin.String? = null,

    @Json(name = "userUpdated")
    val userUpdated: kotlin.String? = null,
)
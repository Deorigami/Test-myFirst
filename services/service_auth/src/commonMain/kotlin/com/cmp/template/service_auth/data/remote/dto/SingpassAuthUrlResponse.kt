package com.cmp.template.service_auth.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SingpassAuthUrlResponse(
    @SerialName("auth_url") val authUrl: String? = null,
    @SerialName("state")    val state: String? = null,
)


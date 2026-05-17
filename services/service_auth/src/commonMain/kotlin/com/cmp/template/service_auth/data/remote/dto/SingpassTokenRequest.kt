package com.cmp.template.service_auth.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request body for the Singpass token exchange.
 *
 * Mock endpoint: POST /functions/v1/singpass-token
 * Real endpoint: POST https://stg-id.singpass.gov.sg/token
 */
@Serializable
data class SingpassTokenRequest(
    @SerialName("code") val code: String,
    @SerialName("grant_type") val grantType: String = "authorization_code",
)

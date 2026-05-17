package com.cmp.template.service_auth.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response from the Singpass token exchange endpoint.
 *
 * Contains the standard OAuth2 token fields plus a convenience
 * `decoded` field with the parsed JWT claims (mock-only).
 */
@Serializable
data class SingpassTokenResponse(
    @SerialName("access_token") val accessToken: String? = null,
    @SerialName("token_type")   val tokenType: String? = null,
    @SerialName("expires_in")   val expiresIn: Int? = null,
    @SerialName("id_token")     val idToken: String? = null,
    @SerialName("decoded")      val decoded: SingpassIdTokenClaims? = null,
)

/**
 * Decoded JWT claims from the Singpass ID token.
 *
 * Maps directly to the JWT payload returned by both the mock
 * and the real Singpass FAPI endpoint.
 */
@Serializable
data class SingpassIdTokenClaims(
    @SerialName("iss")            val issuer: String? = null,
    @SerialName("aud")            val audience: String? = null,
    @SerialName("sub")            val subject: String? = null,
    @SerialName("sub_type")       val subType: String? = null,
    @SerialName("iat")            val issuedAt: Long? = null,
    @SerialName("exp")            val expiresAt: Long? = null,
    @SerialName("nonce")          val nonce: String? = null,
    @SerialName("acr")            val acr: String? = null,
    @SerialName("amr")            val amr: List<String>? = null,
    @SerialName("sub_attributes") val subAttributes: SingpassSubAttributes? = null,
)

/**
 * User profile attributes from the Singpass ID token.
 */
@Serializable
data class SingpassSubAttributes(
    @SerialName("account_type")    val accountType: String? = null,
    @SerialName("identity_number") val identityNumber: String? = null,
    @SerialName("identity_coi")    val identityCoi: String? = null,
    @SerialName("name")            val name: String? = null,
    @SerialName("email")           val email: String? = null,
    @SerialName("mobileno")        val mobileNo: String? = null,
)

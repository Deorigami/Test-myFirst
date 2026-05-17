package com.cmp.template.service_auth.domain.entity

import kotlinx.serialization.Serializable

/**
 * Domain entity representing the authenticated Singpass user.
 * Mapped from the decoded JWT claims returned by the token exchange.
 */
@Serializable
data class SingpassUser(
    val sub: String,
    val identityNumber: String,
    val name: String,
    val email: String,
    val mobileNo: String,
    val identityCoi: String = "SG",
    val accountType: String = "standard",
)

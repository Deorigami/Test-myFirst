package com.cmp.template.service_auth.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class SingpassAuthUrl(
    val authUrl: String,
    val state: String,
)


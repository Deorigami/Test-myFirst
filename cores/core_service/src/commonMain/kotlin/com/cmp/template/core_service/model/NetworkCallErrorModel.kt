package com.cmp.template.core_service.model
import kotlinx.serialization.Serializable
@Serializable
data class NetworkCallErrorModel(
    val status: String? = null,
    val code: String? = null,
    val message: String? = null
)

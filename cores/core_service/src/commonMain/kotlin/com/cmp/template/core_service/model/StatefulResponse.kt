package com.cmp.template.core_service.model
import kotlinx.serialization.Serializable
@Serializable
data class StatefulResponse<T>(
    val data: T? = null,
    val message: String? = null,
    val success: Boolean = false
)

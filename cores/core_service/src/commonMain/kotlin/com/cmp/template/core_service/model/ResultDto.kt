package com.cmp.template.core_service.model
import kotlinx.serialization.Serializable
@Serializable
data class ResultDto<T>(
    val results: List<T>? = null,
    val total: Int? = null
)

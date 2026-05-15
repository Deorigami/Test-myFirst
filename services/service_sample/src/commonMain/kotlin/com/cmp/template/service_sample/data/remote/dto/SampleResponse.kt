package com.cmp.template.service_sample.data.remote.dto
import kotlinx.serialization.Serializable
@Serializable
data class SampleResponse(
    val id: Int? = null,
    val title: String? = null,
    val body: String? = null
)

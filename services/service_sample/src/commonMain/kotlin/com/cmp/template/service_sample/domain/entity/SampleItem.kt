package com.cmp.template.service_sample.domain.entity
import kotlinx.serialization.Serializable
@Serializable
data class SampleItem(
    val id: Int,
    val title: String,
    val description: String
)

package com.cmp.template.feature_sample.landing
import com.cmp.template.service_sample.domain.entity.SampleItem
data class SampleScreenState(
    val items: List<SampleItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

package com.cmp.template.feature_sample.landing
sealed interface SampleScreenEvent {
    data object LoadItems : SampleScreenEvent
    data object Refresh : SampleScreenEvent
}

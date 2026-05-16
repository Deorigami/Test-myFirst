package com.cmp.template.core_service.connection

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/** Shared base configuration applied to every platform's [HttpClient]. */
internal fun HttpClientConfig<*>.applyBaseConfig() {
    install(ContentNegotiation) {
        json(Json {
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}

/**
 * Builds a platform-appropriate [HttpClient]:
 * - **Android** — OkHttp engine + Chucker interceptor (full copy/share support)
 * - **iOS / JVM** — default engine + Axer plugin
 */
expect fun buildHttpClient(): HttpClient


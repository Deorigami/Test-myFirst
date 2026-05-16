package com.cmp.template.core_service.connection

import io.github.orioneee.Axer
import io.ktor.client.HttpClient

actual fun buildHttpClient(): HttpClient = HttpClient {
    applyBaseConfig()
    install(Axer.ktorPlugin)
}


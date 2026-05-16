package com.cmp.template.core_service.connection

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.context.GlobalContext

actual fun buildHttpClient(): HttpClient {
    // Koin is already started before any singleton is first resolved.
    val app = GlobalContext.get().get<Application>()

    return HttpClient(OkHttp) {
        applyBaseConfig()
        engine {
            addInterceptor(
                ChuckerInterceptor.Builder(app)
                    .collector(ChuckerCollector(app))
                    .maxContentLength(250_000L)
                    .alwaysReadResponseBody(true)
                    .build()
            )
        }
    }
}


package com.cmp.template.core_service.connection
import com.cmp.template.core_service.base.Constant
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
@Module
@ComponentScan("com.cmp.template")
class ServiceModule {
    @Single
    fun provideKtorfit(): Ktorfit = Ktorfit.Builder()
        .baseUrl(Constant.BASE_URL)
        .httpClient(HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        })
        .build()
}

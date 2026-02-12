package app.tktn.service_news

import app.tktn.service_news.data.remote.NewsApi
import androidx.room.RoomDatabase
import app.tktn.service_news.data.remote.createNewsApi
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.qualifier.named

@Module
@ComponentScan("app.tktn.service_news")
class ServiceNewsModule {
    @Single
    fun provideNewsApi(ktorfit: Ktorfit): NewsApi = ktorfit.createNewsApi()
}
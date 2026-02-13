package app.tktn.service_news.data

import org.koin.core.annotation.Single

@Single
class NewsConfig {
    // this would be fetched from BuildConfig or an encrypted store
    val apiKey: String = "79a1436988f44403a63332db5b80c840"
}

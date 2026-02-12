package app.tktn.service_news.data.remote

import app.tktn.core_service.base.Constant
import app.tktn.service_news.data.remote.dto.NewsResponse
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
		@Query("country") country: String = "us",
		@Query("pageSize") pageSize: Int = Constant.PaginationPageSize,
		@Query("page") page: Int,
		@Query("apiKey") apiKey: String
    ): NewsResponse

    @GET("v2/everything")
    suspend fun searchNews(
		@Query("q") query: String,
		@Query("pageSize") pageSize: Int = Constant.PaginationPageSize,
		@Query("page") page: Int,
		@Query("apiKey") apiKey: String
    ): NewsResponse
}

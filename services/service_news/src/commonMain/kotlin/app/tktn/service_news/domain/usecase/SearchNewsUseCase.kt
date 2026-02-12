package app.tktn.service_news.domain.usecase

import app.tktn.core_service.base.BaseUseCase
import app.tktn.core_service.model.DomainResult
import app.tktn.service_news.domain.entity.NewsArticle
import app.tktn.service_news.domain.repository.NewsRepository
import org.koin.core.annotation.Factory

data class SearchNewsParam(
    val query: String,
    val page: Int
)

@Factory
class SearchNewsUseCase(
    private val repository: NewsRepository
) : BaseUseCase<SearchNewsParam, List<NewsArticle>>() {
    override val default: List<NewsArticle> = emptyList()

    override suspend fun build(param: SearchNewsParam): DomainResult<List<NewsArticle>> {
        return repository.searchNews(param.query, param.page)
    }
}

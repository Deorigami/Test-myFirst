package app.tktn.service_news.domain.usecase

import app.tktn.core_service.base.BaseUseCase
import app.tktn.core_service.model.DomainResult
import app.tktn.service_news.domain.entity.NewsArticle
import app.tktn.service_news.domain.repository.NewsRepository
import org.koin.core.annotation.Factory

@Factory
class GetTopHeadlinesUseCase(
    private val repository: NewsRepository
) : BaseUseCase<Int, List<NewsArticle>>() {
    override val default: List<NewsArticle> = emptyList()

    override suspend fun build(param: Int): DomainResult<List<NewsArticle>> {
        return repository.getTopHeadlines(param)
    }
}

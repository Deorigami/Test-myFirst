package app.tktn.service_news.domain.usecase

import app.tktn.core_service.base.FlowUseCase
import app.tktn.service_news.domain.entity.NewsArticle
import app.tktn.service_news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class GetCachedHeadlinesUseCase(
    private val repository: NewsRepository
) : FlowUseCase<Unit, List<NewsArticle>>() {
    override fun build(param: Unit): Flow<List<NewsArticle>> {
        return repository.getCachedHeadlines()
    }
}

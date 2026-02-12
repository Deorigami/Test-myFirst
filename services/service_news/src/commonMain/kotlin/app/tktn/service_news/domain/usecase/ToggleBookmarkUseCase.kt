package app.tktn.service_news.domain.usecase

import app.tktn.core_service.base.BaseUseCase
import app.tktn.core_service.model.DomainResult
import app.tktn.service_news.domain.entity.NewsArticle
import app.tktn.service_news.domain.repository.NewsRepository
import org.koin.core.annotation.Factory

@Factory
class ToggleBookmarkUseCase(
    private val repository: NewsRepository
) : BaseUseCase<NewsArticle, Unit>() {
    override val default: Unit = Unit

    override suspend fun build(param: NewsArticle): DomainResult<Unit> {
        repository.toggleBookmark(param)
        return DomainResult(Unit)
    }
}

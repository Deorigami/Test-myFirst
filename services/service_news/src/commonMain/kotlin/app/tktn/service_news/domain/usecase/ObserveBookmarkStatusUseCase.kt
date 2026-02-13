package app.tktn.service_news.domain.usecase

import app.tktn.core_service.base.FlowUseCase
import app.tktn.service_news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class ObserveBookmarkStatusUseCase(
    private val repository: NewsRepository
) : FlowUseCase<String, Boolean>() {
    override fun build(param: String): Flow<Boolean> {
        return repository.observeBookmarkStatus(param)
    }
}

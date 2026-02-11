package app.tktn.service_feed.domain.usecase

import app.tktn.core_service.base.BaseUseCase
import app.tktn.core_service.model.DomainResult
import app.tktn.service_feed.domain.entity.StoryEntity
import app.tktn.service_feed.domain.repository.FeatureFeedRepository
import org.koin.core.annotation.Factory

@Factory
class GetStoriesUseCase(
	private val repository: FeatureFeedRepository
) : BaseUseCase<Unit, List<StoryEntity>>() {
	override val default: List<StoryEntity>
		get() = emptyList()

	override suspend fun build(param: Unit): DomainResult<List<StoryEntity>> {
		return repository.getStories()
	}
}
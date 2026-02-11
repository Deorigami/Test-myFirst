package app.tktn.service_feed.data.repository

import app.tktn.core_service.model.DomainResult
import app.tktn.core_service.model.ResultDto.Companion.toDomainResult
import app.tktn.service_feed.data.FeatureFeedService
import app.tktn.service_feed.domain.repository.FeatureFeedRepository
import app.tktn.service_feed.domain.entity.StoryEntity
import org.koin.core.annotation.Factory

@Factory
class ServiceFeedRepositoryImpl(
	private val service: FeatureFeedService
) : FeatureFeedRepository {
	override suspend fun getStories(): DomainResult<List<StoryEntity>> {
		return service.getStories().toDomainResult {
			it.contents.mapIndexed {idx, item ->
				StoryEntity(
					user = item.user,
					description = item.description,
					image = "https://i.pravatar.cc/150?img=${idx + 10}"
				)
			}
		}
	}
}
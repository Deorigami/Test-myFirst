package app.tktn.service_feed.domain.repository

import app.tktn.core_service.model.DomainResult
import app.tktn.core_service.model.ResultDto
import app.tktn.service_feed.data.dto.StoryDto
import app.tktn.service_feed.domain.entity.StoryEntity

interface FeatureFeedRepository {
	suspend fun getStories() : DomainResult<List<StoryEntity>>
}
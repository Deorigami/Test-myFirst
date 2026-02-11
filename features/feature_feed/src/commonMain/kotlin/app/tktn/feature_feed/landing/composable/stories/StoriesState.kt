package app.tktn.feature_feed.landing.composable.stories

import app.tktn.service_feed.data.dto.StoryDto
import app.tktn.service_feed.domain.entity.StoryEntity

data class StoriesState(
	val stories: List<StoryEntity> = emptyList()
)
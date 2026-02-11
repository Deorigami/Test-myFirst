package app.tktn.feature_feed.landing.composable.stories

import app.tktn.service_feed.data.dto.StoryDto

sealed interface StoriesEvent {
	data object RefreshData : StoriesEvent
}
package app.tktn.service_feed.data

import app.tktn.core_service.model.ResultDto
import app.tktn.service_feed.data.dto.ContentsDto
import app.tktn.service_feed.data.dto.StoryDto
import de.jensklingenberg.ktorfit.http.GET

interface FeatureFeedService {
	@GET("contents")
	suspend fun getStories() : ResultDto<ContentsDto>
}
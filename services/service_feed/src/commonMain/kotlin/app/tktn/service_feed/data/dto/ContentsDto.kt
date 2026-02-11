package app.tktn.service_feed.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ContentsDto(
	val contents: List<StoryDto>
)

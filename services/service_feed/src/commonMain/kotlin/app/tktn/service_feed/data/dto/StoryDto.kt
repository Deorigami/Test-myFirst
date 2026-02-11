package app.tktn.service_feed.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoryDto(
	val user: String,
	@SerialName("deskripsi")
	val description: String
)

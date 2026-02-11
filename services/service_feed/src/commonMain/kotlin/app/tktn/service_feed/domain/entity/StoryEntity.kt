package app.tktn.service_feed.domain.entity

data class StoryEntity(
	val user: String,
	val description: String,

	// can additional field because the api is so simple
	val image: String
)
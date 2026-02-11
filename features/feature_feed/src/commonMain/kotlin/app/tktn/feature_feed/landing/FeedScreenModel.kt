package app.tktn.feature_feed.landing

import app.tktn.core_feature.base.BaseScreenModel
import app.tktn.feature_feed.landing.model.Post
import app.tktn.service_feed.domain.usecase.GetStoriesUseCase
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class FeedScreenModel(
) : BaseScreenModel<FeedScreenState, FeedScreenEvent>(FeedScreenState()) {

	init {
		generateMockPosts()
	}

	private fun generateMockPosts() {
		val mockPosts = List(20) { index ->
			val hasVideo = index % 2 == 0
			val imageCount = when {
				index % 4 == 0 -> 2
				index % 4 == 1 -> 1
				index % 4 == 2 -> 0
				index % 4 == 3 -> 3
				else -> 1
			}

			Post(
				id = index.toString(),
				username = "awesome_user_$index",
				userAvatar = "https://i.pravatar.cc/150?img=${index + 20}",
				hasVideo = hasVideo,
				videoUrl = if (hasVideo) "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4" else null,
				images = List(imageCount) { imageIndex ->
					"https://picsum.photos/seed/${index + 100 + imageIndex}/600/600"
				},
				likesCount = (index + 1) * 123,
				caption = "Exploring the dynamic feed features today! Video or Image? Why not both! #jetpackcompose #multiplatform",
				commentCount = (index + 1) * 5,
				timeAgo = "${index + 1} hours ago"
			)
		}
		updateState { it.copy(posts = mockPosts) }
	}

    override fun onEvent(event: FeedScreenEvent) {

    }
}
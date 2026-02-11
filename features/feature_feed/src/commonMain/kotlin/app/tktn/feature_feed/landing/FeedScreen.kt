package app.tktn.feature_feed.landing

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.tktn.core_feature.base.BaseScreen
import app.tktn.feature_feed.landing.composable.post.PostItem
import app.tktn.feature_feed.landing.composable.stories.StoriesRow
import org.koin.compose.viewmodel.koinViewModel

object FeedScreen : BaseScreen() {
    override val pageName: String?
        get() = this::class.simpleName

    @Composable
    override fun ComposeContent() {
        val viewModel: FeedScreenModel = koinViewModel()
        val state by viewModel.uiState.collectAsState()

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                StoriesRow()
                HorizontalDivider(
                    color = Color.LightGray.copy(alpha = 0.3f),
                    thickness = 0.5.dp
                )
            }

            items(state.posts, key = { it.id }) { post ->
                PostItem(post = post)
            }
        }
    }
}
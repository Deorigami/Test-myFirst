package app.tktn.feature_news.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.tktn.core_feature.base.BaseScreen
import app.tktn.core_feature.navigation.LocalNavStack
import app.tktn.feature_news.composable.NewsArticleItem
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
object BookmarkScreen : BaseScreen() {
	@Composable
	override fun ComposeContent() {
		val viewModel: BookmarkScreenModel = koinViewModel()
		val state by viewModel.uiState.collectAsState()
		val navStack = LocalNavStack.current
		val scrollBehavior =
			TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

		Scaffold(
			modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
			topBar = {
				LargeTopAppBar(
					title = {
						Text(
							"Bookmarks",
							fontWeight = FontWeight.Black,
							modifier = Modifier.padding(horizontal = 8.dp)
						)
					},
					scrollBehavior = scrollBehavior
				)
			}
		) { paddingValues ->
			Box(
				modifier = Modifier.fillMaxSize()
					.padding(paddingValues)
			) {
				if (state.isLoading && state.articles.isEmpty()) {
					CircularProgressIndicator(
						modifier = Modifier.align(
							Alignment.Center
						)
					)
				} else if (state.articles.isEmpty()) {
					Column(
						modifier = Modifier.align(Alignment.Center),
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						Text(
							text = "Your bookmarks will appear here",
							style = MaterialTheme.typography.titleMedium,
							color = MaterialTheme.colorScheme.outline
						)
						Spacer(modifier = Modifier.height(8.dp))
						Text(
							text = "Save articles to read them later",
							style = MaterialTheme.typography.bodyMedium,
							color = MaterialTheme.colorScheme.outline.copy(
								alpha = 0.7f
							)
						)
					}
				} else {
					LazyColumn(
						modifier = Modifier.fillMaxSize(),
						contentPadding = PaddingValues(bottom = 16.dp)
					) {
						items(
							state.articles,
							key = { it.url }) { article ->
							NewsArticleItem(
								article = article,
								onClick = {
//									navStack.add(NavDestinations.NewsDetail(article))
								},
								onBookmarkClick = {
									viewModel.onEvent(
										BookmarkScreenEvent.ToggleBookmark(
											article
										)
									)
								}
							)
						}
					}
				}
			}
		}
	}
}

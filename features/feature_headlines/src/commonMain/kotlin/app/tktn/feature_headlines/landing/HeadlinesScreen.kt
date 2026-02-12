package app.tktn.feature_headlines.landing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.tktn.components.composable.NewsArticleItem
import app.tktn.core_feature.base.BaseScreen
import app.tktn.feature_headlines.di.FeatureHeadlinesNavigation
import co.touchlab.kermit.Logger
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
object HeadlinesScreen : BaseScreen() {

	@Composable
	override fun ComposeContent() {
		val viewModel: HeadlinesScreenModel = koinViewModel()
		val state by viewModel.uiState.collectAsState()
		val listState = rememberLazyListState()
		val scrollBehavior =
			TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
		// Pagination check
		LaunchedEffect(state, listState){
			Logger.d("LaunchedEffect(state)"){ "IsLastPage : ${state.isLastPage} | isLoading : ${state.isLoading} | isLoadingNextPage : ${state.isLoadingNextPage} | lastVisibleItemIndex : ${listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index}" }
		}
		val shouldLoadMore by remember {
			derivedStateOf {
				val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
						?: 0
				lastVisibleItemIndex >= state.articles.size - 1 && !state.isLastPage && !state.isLoadingNextPage && !state.isLoading
			}
		}
		val navigation = koinInject<FeatureHeadlinesNavigation>()

		LaunchedEffect(shouldLoadMore) {
			Logger.d("LaunchedEffect(shouldLoadMore)") { shouldLoadMore.toString() }
			if (shouldLoadMore) {
				viewModel.onEvent(HeadlinesScreenEvent.LoadNextPage)
			}
		}

		Scaffold(
			modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
			topBar = {
				TopAppBar(
					title = {
						Text(
							"Top Headlines",
							fontWeight = FontWeight.Black,
							modifier = Modifier.padding(horizontal = 8.dp)
						)
					},
					actions = {
						IconButton(onClick = {
							viewModel.onEvent(
								HeadlinesScreenEvent.Refresh
							)
						}) {
							Icon(
								Icons.Default.Refresh,
								contentDescription = "Refresh"
							)
						}
					},
					scrollBehavior = scrollBehavior,
					colors = TopAppBarDefaults.largeTopAppBarColors(
						containerColor = MaterialTheme.colorScheme.background,
						scrolledContainerColor = MaterialTheme.colorScheme.surface
					)
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
				} else {
					LazyColumn(
						state = listState,
						modifier = Modifier.fillMaxSize(),
						contentPadding = PaddingValues(bottom = 16.dp)
					) {
						if (state.isOffline) {
							item {
								Surface(
									color = MaterialTheme.colorScheme.errorContainer,
									modifier = Modifier.fillMaxWidth()
										.padding(
											horizontal = 16.dp,
											vertical = 8.dp
										),
									shape = MaterialTheme.shapes.medium
								) {
									Text(
										text = "Offline Mode - Showing cached news",
										modifier = Modifier.padding(12.dp),
										style = MaterialTheme.typography.labelMedium,
										color = MaterialTheme.colorScheme.onErrorContainer
									)
								}
							}
						}

						items(
							state.articles,
							key = { it.url }) { article ->
							NewsArticleItem(
								onClick = {
									navigation.navigateToNewsDetail(
										article
									)
								},
								onBookmarkClick = {
									viewModel.onEvent(
										HeadlinesScreenEvent.ToggleBookmark(
											article
										)
									)
								},
								title = article.title,
								author = article.author,
								description = article.description,
								url = article.url,
								urlToImage = article.urlToImage,
								publishedAt = article.publishedAt,
								content = article.content,
								isBookmarked = article.isBookmarked,
								modifier = Modifier
							)
						}

						if (state.isLoadingNextPage) {
							item {
								Box(
									modifier = Modifier.fillMaxWidth()
										.padding(16.dp),
									contentAlignment = Alignment.Center
								) {
									CircularProgressIndicator(
										modifier = Modifier.size(
											24.dp
										)
									)
								}
							}
						}

						state.error?.let {
							item {
								Text(
									text = "Error: $it",
									color = MaterialTheme.colorScheme.error,
									modifier = Modifier.padding(16.dp)
								)
							}
						}
					}
				}
			}
		}
	}
}

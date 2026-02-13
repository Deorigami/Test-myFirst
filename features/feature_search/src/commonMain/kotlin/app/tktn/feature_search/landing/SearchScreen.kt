package app.tktn.feature_search.landing

//import app.tktn.attendees_check.navigation.NavDestinations
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import app.tktn.core_service.extension.toFormattedDate
import app.tktn.feature_search.di.FeatureSearchNavigation
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
object SearchScreen : BaseScreen() {
	@Composable
	override fun ComposeContent() {
		val viewModel: SearchScreenModel = koinViewModel()
		val state by viewModel.uiState.collectAsState()
		val listState = rememberLazyListState()
		val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
		val navigation = koinInject<FeatureSearchNavigation>()


		val shouldLoadMore = remember {
			derivedStateOf {
				val lastVisibleItemIndex =
					listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
						?: 0
				lastVisibleItemIndex >= state.articles.size - 1 && !state.isLastPage && !state.isLoadingNextPage && !state.isLoading
			}
		}

		LaunchedEffect(shouldLoadMore.value) {
			if (shouldLoadMore.value) {
				viewModel.onEvent(SearchScreenEvent.LoadNextPage)
			}
		}

		Scaffold(
			modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
			topBar = {
				TopAppBar(
					title = {
						Text(
							"Search",
							fontWeight = FontWeight.Black,
							modifier = Modifier.padding(horizontal = 8.dp)
						)
					},
					scrollBehavior = scrollBehavior
				)
			}
		) { paddingValues ->
			Column(
				modifier = Modifier.fillMaxSize()
					.padding(paddingValues)
			) {
				OutlinedTextField(
					value = state.query,
					onValueChange = {
						viewModel.onEvent(
							SearchScreenEvent.UpdateQuery(it)
						)
					},
					modifier = Modifier.fillMaxWidth().padding(16.dp),
					placeholder = { Text("Search news topics...") },
					shape = MaterialTheme.shapes.large,
					leadingIcon = {
						Icon(
							Icons.Default.Search,
							contentDescription = null
						)
					},
					trailingIcon = {
						if (state.query.isNotEmpty()) {
							Button(
								onClick = {
									viewModel.onEvent(
										SearchScreenEvent.Search
									)
								},
								modifier = Modifier.padding(end = 4.dp),
								shape = MaterialTheme.shapes.medium
							) {
								Text("Go")
							}
						}
					},
					singleLine = true
				)

				Box(modifier = Modifier.fillMaxSize()) {
					if (state.isLoading && state.articles.isEmpty()) {
						CircularProgressIndicator(
							modifier = Modifier.align(
								Alignment.Center
							)
						)
					} else if (state.articles.isEmpty() && state.query.isNotEmpty() && !state.isLoading) {
						Text(
							"No results found",
							modifier = Modifier.align(Alignment.Center),
							style = MaterialTheme.typography.bodyLarge
						)
					} else {
						LazyColumn(
							state = listState,
							modifier = Modifier.fillMaxSize(),
							contentPadding = PaddingValues(bottom = 16.dp)
						) {
							items(
								state.articles,
								key = { it.url }) { article ->
								NewsArticleItem(
									onClick = {
										navigation.navigateToNewsDetail(article)
									},
									onBookmarkClick = {
										viewModel.onEvent(
											SearchScreenEvent.ToggleBookmark(
												article
											)
										)
									},
									title = article.title,
									author = article.author,
									description = article.description,
									url = article.url,
									urlToImage = article.urlToImage,
									publishedAt = article.publishedAt.toFormattedDate(),
									content = article.content,
									isBookmarked = article.isBookmarked,
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
						}
					}
				}
			}
		}
	}
}

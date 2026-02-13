package app.tktn.feature_search.landing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.material3.TopAppBarScrollBehavior
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
import app.tktn.service_news.domain.entity.NewsArticle
import app.tktn.components.Res
import app.tktn.components.search
import app.tktn.components.search_placeholder
import app.tktn.components.no_results
import app.tktn.components.go
import org.jetbrains.compose.resources.stringResource
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

        SearchScreenContent(
            state = state,
            listState = listState,
            scrollBehavior = scrollBehavior,
            onEvent = viewModel::onEvent,
            onNavigateToDetail = { article -> navigation.navigateToNewsDetail(article) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreenContent(
    state: SearchScreenState,
    listState: LazyListState,
    scrollBehavior: TopAppBarScrollBehavior,
    onEvent: (SearchScreenEvent) -> Unit,
    onNavigateToDetail: (NewsArticle) -> Unit
) {
    val shouldLoadMore = remember(listState) {
        derivedStateOf {
            val lastVisibleItemIndex =
                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItemIndex >= state.articles.size - 1 && !state.isLastPage && !state.isLoadingNextPage && !state.isLoading
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            onEvent(SearchScreenEvent.LoadNextPage)
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { SearchTopBar(scrollBehavior) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SearchBar(
                query = state.query,
                onQueryChange = { onEvent(SearchScreenEvent.UpdateQuery(it)) },
                onSearchClick = { onEvent(SearchScreenEvent.Search) }
            )

            SearchContent(
                isLoading = state.isLoading,
                articles = state.articles,
                query = state.query,
                error = state.error,
                isLoadingNextPage = state.isLoadingNextPage,
                listState = listState,
                onNavigateToDetail = onNavigateToDetail,
                onBookmarkClick = { article -> onEvent(SearchScreenEvent.ToggleBookmark(article)) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTopBar(scrollBehavior: TopAppBarScrollBehavior) {
    TopAppBar(
        title = {
            Text(
                stringResource(Res.string.search),
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text(stringResource(Res.string.search_placeholder)) },
        shape = MaterialTheme.shapes.large,
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                Button(
                    onClick = onSearchClick,
                    modifier = Modifier.padding(end = 4.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(stringResource(Res.string.go))
                }
            }
        },
        singleLine = true
    )
}

@Composable
private fun SearchContent(
    isLoading: Boolean,
    articles: List<NewsArticle>,
    query: String,
    error: String?,
    isLoadingNextPage: Boolean,
    listState: LazyListState,
    onNavigateToDetail: (NewsArticle) -> Unit,
    onBookmarkClick: (NewsArticle) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading && articles.isEmpty()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (articles.isEmpty() && query.isNotEmpty() && !isLoading) {
            Text(
                stringResource(Res.string.no_results),
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            ArticleList(
                articles = articles,
                listState = listState,
                error = error,
                isLoadingNextPage = isLoadingNextPage,
                onNavigateToDetail = onNavigateToDetail,
                onBookmarkClick = onBookmarkClick
            )
        }
    }
}

@Composable
private fun ArticleList(
    articles: List<NewsArticle>,
    listState: LazyListState,
    error: String?,
    isLoadingNextPage: Boolean,
    onNavigateToDetail: (NewsArticle) -> Unit,
    onBookmarkClick: (NewsArticle) -> Unit
) {
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(
            items = articles,
            key = { it.url }
        ) { article ->
            NewsArticleItem(
                onClick = { onNavigateToDetail(article) },
                onBookmarkClick = { onBookmarkClick(article) },
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

        if (isLoadingNextPage) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
            }
        }

        error?.let {
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

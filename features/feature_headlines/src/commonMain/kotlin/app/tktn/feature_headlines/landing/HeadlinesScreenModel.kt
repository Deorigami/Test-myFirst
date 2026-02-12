package app.tktn.feature_headlines.landing

import androidx.lifecycle.viewModelScope
import app.tktn.core_feature.base.BaseScreenModel
import app.tktn.core_service.model.StatefulResult.Companion.onSuccess
import app.tktn.service_news.domain.entity.NewsArticle
import app.tktn.service_news.domain.usecase.GetCachedHeadlinesUseCase
import app.tktn.service_news.domain.usecase.GetTopHeadlinesUseCase
import app.tktn.service_news.domain.usecase.ToggleBookmarkUseCase
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HeadlinesScreenModel(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
	private val getCachedTopHeadlinesUseCase: GetCachedHeadlinesUseCase
) : BaseScreenModel<HeadlinesScreenState, HeadlinesScreenEvent>(HeadlinesScreenState()) {

    init {
        observeCache()
        onEvent(HeadlinesScreenEvent.LoadHeadlines)
    }

    private fun observeCache() {
        viewModelScope.launch {
			getCachedTopHeadlinesUseCase(Unit).collectLatest { cached ->
                if (currentState.articles.isEmpty() && cached.isNotEmpty()) {
                    updateState { it.copy(articles = cached, isOffline = true) }
                } else {
					val updatedCache = cached.mapNotNull { article ->
						currentState.articles.firstOrNull { it.url == article.url }?.copy(isBookmarked = article.isBookmarked)
					}
					updateState { it.copy(articles = updatedCache) }
				}
            }
        }
    }

    override fun onEvent(event: HeadlinesScreenEvent) {
        when (event) {
            is HeadlinesScreenEvent.LoadHeadlines -> loadHeadlines(refresh = true)
            is HeadlinesScreenEvent.LoadNextPage -> loadHeadlines(refresh = false)
            is HeadlinesScreenEvent.Refresh -> loadHeadlines(refresh = true)
            is HeadlinesScreenEvent.ToggleBookmark -> toggleBookmark(event.article)
        }
    }

    private fun loadHeadlines(refresh: Boolean) {
        if (currentState.isLoading || currentState.isLoadingNextPage) return
        if (!refresh && currentState.isLastPage) return

        val nextPage = if (refresh) 1 else currentState.page + 1

        updateState { 
            if (refresh) it.copy(isLoading = true, error = null)
            else it.copy(isLoadingNextPage = true, error = null)
        }

        viewModelScope.launch {
            getTopHeadlinesUseCase.execute(viewModelScope, nextPage) { result ->
                result.onSuccess { newArticles ->
                    updateState { state ->
                        val updatedArticles = if (refresh) newArticles else state.articles + newArticles
                        state.copy(
                            articles = updatedArticles,
                            page = nextPage,
                            isLastPage = newArticles.size < 5,
                            isLoading = false,
                            isLoadingNextPage = false,
                            isOffline = false
                        )
                    }
                }
            }
        }
    }

    private fun toggleBookmark(article: NewsArticle) {
        toggleBookmarkUseCase.execute(viewModelScope, article)
    }
}

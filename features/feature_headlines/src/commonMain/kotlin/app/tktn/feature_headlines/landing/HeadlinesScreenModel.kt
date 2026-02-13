package app.tktn.feature_headlines.landing

import androidx.lifecycle.viewModelScope
import app.tktn.core_feature.base.BaseScreenModel
import app.tktn.core_service.model.StatefulResult.Companion.onError
import app.tktn.core_service.model.StatefulResult.Companion.onSuccess
import app.tktn.service_news.domain.entity.NewsArticle
import app.tktn.service_news.domain.usecase.GetCachedHeadlinesUseCase
import app.tktn.service_news.domain.usecase.GetTopHeadlinesUseCase
import app.tktn.service_news.domain.usecase.ToggleBookmarkUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

import app.tktn.core_feature.connectivity.ConnectivityViewModel

@KoinViewModel
class HeadlinesScreenModel(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
	private val getCachedTopHeadlinesUseCase: GetCachedHeadlinesUseCase,
    private val connectivityViewModel: ConnectivityViewModel
) : BaseScreenModel<HeadlinesScreenState, HeadlinesScreenEvent>(HeadlinesScreenState()) {

    init {
        observeConnectivity()
        observeCache()
    }

    private fun observeConnectivity() {
        viewModelScope.launch {
            connectivityViewModel.isConnectedFlow.collectLatest { isConnected ->
                if (isConnected) {
                    onEvent(HeadlinesScreenEvent.LoadHeadlines)
                } else {
                    updateState { it.copy(isOffline = true) }
                }
            }
        }
    }

    private fun observeCache() {
        viewModelScope.launch {
            getCachedTopHeadlinesUseCase(Unit).collectLatest { cached ->
                updateState { it.copy(articles = cached) }
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

        if (!connectivityViewModel.isConnectedFlow.value) {
            updateState { it.copy(isOffline = true, isLoading = false, isLoadingNextPage = false) }
            return
        }

        val nextPage = if (refresh) 1 else currentState.page + 1

        updateState { 
            if (refresh) it.copy(isLoading = true, error = null)
            else it.copy(isLoadingNextPage = true, error = null)
        }

        viewModelScope.launch {
            getTopHeadlinesUseCase.execute(viewModelScope, nextPage) { result ->
                result.onSuccess { newArticles ->
                    updateState { state ->
                        state.copy(
                            page = nextPage,
                            isLastPage = newArticles.size < 5,
                            isLoading = false,
                            isLoadingNextPage = false,
                            isOffline = false,
                            error = null
                        )
                    }
                }
                result.onError { error ->
                    updateState {
                        it.copy(
                            isLoading = false,
                            isLoadingNextPage = false,
                            error = error?.message ?: "Unknown error"
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

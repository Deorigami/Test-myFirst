package app.tktn.feature_news.search

import androidx.lifecycle.viewModelScope
import app.tktn.core_feature.base.BaseScreenModel
import app.tktn.service_news.domain.entity.NewsArticle
import app.tktn.service_news.domain.repository.NewsRepository
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SearchScreenModel(
    private val repository: NewsRepository
) : BaseScreenModel<SearchScreenState, SearchScreenEvent>(SearchScreenState()) {

    override fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.UpdateQuery -> updateState { it.copy(query = event.query) }
            is SearchScreenEvent.Search -> search(refresh = true)
            is SearchScreenEvent.LoadNextPage -> search(refresh = false)
            is SearchScreenEvent.ToggleBookmark -> toggleBookmark(event.article)
        }
    }

    private fun search(refresh: Boolean) {
        if (currentState.query.isBlank()) return
        if (currentState.isLoading || currentState.isLoadingNextPage) return
        if (!refresh && currentState.isLastPage) return

        val nextPage = if (refresh) 1 else currentState.page + 1

        updateState { 
            if (refresh) it.copy(isLoading = true, error = null, articles = if (refresh) emptyList() else it.articles)
            else it.copy(isLoadingNextPage = true, error = null)
        }

        viewModelScope.launch {
            val result = repository.searchNews(currentState.query, nextPage)
            result.onSuccess { newArticles ->
                updateState { state ->
                    val updatedArticles = if (refresh) newArticles else state.articles + newArticles
                    state.copy(
                        articles = updatedArticles,
                        page = nextPage,
                        isLastPage = newArticles.size < 5,
                        isLoading = false,
                        isLoadingNextPage = false
                    )
                }
            }.onFailure { error ->
                updateState { 
                    it.copy(
                        isLoading = false, 
                        isLoadingNextPage = false,
                        error = error.message ?: "Unknown error"
                    ) 
                }
            }
        }
    }

    private fun toggleBookmark(article: NewsArticle) {
        viewModelScope.launch {
            repository.toggleBookmark(article)
            updateState { state ->
                val updated = state.articles.map { 
                    if (it.url == article.url) it.copy(isBookmarked = !it.isBookmarked)
                    else it
                }
                state.copy(articles = updated)
            }
        }
    }
}

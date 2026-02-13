package app.tktn.feature_search.landing

import androidx.lifecycle.viewModelScope
import app.tktn.core_feature.base.BaseScreenModel
import app.tktn.core_service.model.StatefulResult.Companion.onError
import app.tktn.core_service.model.StatefulResult.Companion.onSuccess
import app.tktn.service_news.domain.entity.NewsArticle
import app.tktn.service_news.domain.usecase.GetBookmarkedNewsUseCase
import app.tktn.service_news.domain.usecase.SearchNewsParam
import app.tktn.service_news.domain.usecase.SearchNewsUseCase
import app.tktn.service_news.domain.usecase.ToggleBookmarkUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SearchScreenModel(
    private val searchNewsUseCase: SearchNewsUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
	private val bookmarkedNewsUseCase: GetBookmarkedNewsUseCase
) : BaseScreenModel<SearchScreenState, SearchScreenEvent>(SearchScreenState()) {

	init {
		observeCache()
	}

	private fun observeCache() {
		viewModelScope.launch {
			bookmarkedNewsUseCase(Unit)
				.collectLatest { cachedArticles ->
					updateState {
						val bookmarkedArticles = it.articles.map { article ->
							article.copy(
								isBookmarked = cachedArticles.firstOrNull { cached -> cached.url == article.url }?.isBookmarked ?: false
							)
						}
						it.copy(
							articles = bookmarkedArticles
						)
					}
				}
		}
	}

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
            searchNewsUseCase.execute(viewModelScope, SearchNewsParam(currentState.query, nextPage)) { result ->
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
                }
				result.onError { error ->
					// can do something here while error occurs
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

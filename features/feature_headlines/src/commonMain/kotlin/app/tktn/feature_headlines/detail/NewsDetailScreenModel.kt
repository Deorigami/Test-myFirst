package app.tktn.feature_headlines.detail

import androidx.lifecycle.viewModelScope
import app.tktn.core_feature.base.BaseScreenModel
import app.tktn.service_news.domain.entity.NewsArticle
import app.tktn.service_news.domain.usecase.ObserveBookmarkStatusUseCase
import app.tktn.service_news.domain.usecase.ToggleBookmarkUseCase
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class NewsDetailScreenModel(
	private val article: NewsArticle,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
    private val observeBookmarkStatusUseCase: ObserveBookmarkStatusUseCase
) : BaseScreenModel<NewsDetailScreenState, NewsDetailScreenEvent>(NewsDetailScreenState()) {

    override fun onEvent(event: NewsDetailScreenEvent) {
        when (event) {
            is NewsDetailScreenEvent.ToggleBookmark -> toggleBookmark(event.article)
        }
    }

	init {
		init()
	}

    private fun init() {
        updateState { it.copy(article = article, isBookmarked = article.isBookmarked) }
        
        viewModelScope.launch {
            observeBookmarkStatusUseCase(article.url).collect { isBookmarked ->
                updateState { it.copy(isBookmarked = isBookmarked) }
            }
        }
    }

    private fun toggleBookmark(article: NewsArticle) {
        toggleBookmarkUseCase.execute(viewModelScope, article)
    }
}

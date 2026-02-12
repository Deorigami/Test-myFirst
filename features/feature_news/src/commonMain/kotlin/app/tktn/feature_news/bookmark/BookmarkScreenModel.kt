package app.tktn.feature_news.bookmark

import androidx.lifecycle.viewModelScope
import app.tktn.core_feature.base.BaseScreenModel
import app.tktn.service_news.domain.entity.NewsArticle
import app.tktn.service_news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class BookmarkScreenModel(
    private val repository: NewsRepository
) : BaseScreenModel<BookmarkScreenState, BookmarkScreenEvent>(BookmarkScreenState()) {

    init {
        observeBookmarks()
    }

    private fun observeBookmarks() {
        updateState { it.copy(isLoading = true) }
        viewModelScope.launch {
            repository.getBookmarkedNews().collectLatest { bookmarked ->
                updateState { it.copy(articles = bookmarked, isLoading = false) }
            }
        }
    }

    override fun onEvent(event: BookmarkScreenEvent) {
        when (event) {
            is BookmarkScreenEvent.LoadBookmarks -> observeBookmarks()
            is BookmarkScreenEvent.ToggleBookmark -> toggleBookmark(event.article)
        }
    }

    private fun toggleBookmark(article: NewsArticle) {
        viewModelScope.launch {
            repository.toggleBookmark(article)
        }
    }
}

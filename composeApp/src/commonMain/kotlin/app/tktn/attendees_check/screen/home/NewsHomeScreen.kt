package app.tktn.attendees_check.screen.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import app.tktn.core_feature.base.BaseScreen
import app.tktn.feature_bookmarks.landing.BookmarkScreen
import app.tktn.feature_headlines.landing.HeadlinesScreen
import app.tktn.feature_search.landing.SearchScreen
import kotlinx.coroutines.launch

object NewsHomeScreen : BaseScreen() {
	@Composable
	override fun ComposeContent() {
		val pagerState = rememberPagerState(pageCount = { 3 })
		val scope = rememberCoroutineScope()

		Scaffold(
			bottomBar = {
				NavigationBar {
					NewsTab.entries.forEach { tab ->
						NavigationBarItem(
							selected = pagerState.currentPage == tab.ordinal,
							onClick = {
								scope.launch {
									pagerState.animateScrollToPage(
										tab.ordinal
									)
								}
							},
							icon = {
								Icon(
									tab.icon,
									contentDescription = tab.title
								)
							},
							label = { Text(tab.title) }
						)
					}
				}
			}
		) { paddingValues ->
			HorizontalPager(
				state = pagerState,
				modifier = Modifier.fillMaxSize()
					.padding(bottom = paddingValues.calculateBottomPadding())
			) { page ->
				when (page) {
					0 -> HeadlinesScreen.ComposableScreen()
					1 -> SearchScreen.ComposableScreen()
					2 -> BookmarkScreen.ComposableScreen()
				}
			}
		}
	}
}

enum class NewsTab(val title: String, val icon: ImageVector) {
	Headlines("Headlines", Icons.Default.Home),
	Search("Search", Icons.Default.Search),
	Bookmarks("Bookmarks", Icons.Default.Bookmark)
}

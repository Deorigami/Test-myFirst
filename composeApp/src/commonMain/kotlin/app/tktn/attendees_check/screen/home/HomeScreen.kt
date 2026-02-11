package app.tktn.attendees_check.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import app.tktn.components.Res
import app.tktn.components.instagram_text_logo
import app.tktn.core_feature.base.BaseScreen
import app.tktn.feature_explore.explore.ExploreScreen
import app.tktn.feature_feed.landing.FeedScreen
import app.tktn.feature_profile.profile.ProfileScreen
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

object HomeScreen : BaseScreen() {
	@Composable
	override fun ComposeContent() {
		InstagramHome()
	}
}

@Composable
fun InstagramHome() {
	val pagerState = rememberPagerState(pageCount = { 5 })
	val scope = rememberCoroutineScope()

	Scaffold(
		modifier = Modifier.fillMaxSize().statusBarsPadding()
			.navigationBarsPadding()
			.imePadding(),
		topBar = {
			InstagramTopBar()
		},
		bottomBar = {
			InstagramBottomBar {
				scope.launch {
					pagerState.scrollToPage(it.ordinal)
				}
			}
		}
	) { paddingValues ->
		HorizontalPager(
			pagerState,
			userScrollEnabled = false
		) {
			Box(modifier = Modifier.padding(paddingValues)) {
				when (it) {
					0 -> FeedScreen.ComposableScreen()
					1 -> ExploreScreen.ComposableScreen()
					2 -> Unit
					3 -> Unit
					4 -> ProfileScreen.ComposableScreen()
				}
			}
		}
	}
}

@Composable
fun InstagramTopBar() {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.height(56.dp)
			.padding(horizontal = 16.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		// Left: Add Icon
		Icon(
			imageVector = Icons.Default.AddCircle,
			contentDescription = "New Post",
			modifier = Modifier.size(24.dp)
		)

		// Center: "For you" / "Following" Dropdown logic
		Image(
			painterResource(Res.drawable.instagram_text_logo),
			"",
			modifier = Modifier.heightIn(max = 30.dp),
			colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
		)

		Icon(
			imageVector = Icons.Outlined.FavoriteBorder,
			contentDescription = "Notifications",
			modifier = Modifier.size(24.dp)
		)
	}
}

enum class HomeBottomBarMenu(
	val icon: ImageVector,
	val label: String
) {
	FEED(Icons.Default.Home, "Feed"),
	SEARCH(Icons.Default.Search, "Search"),
	SEND(Icons.AutoMirrored.Filled.Send, "Send"),
	REELS(Icons.Default.PlayArrow, "Reels"),
	PROFILE(Icons.Default.AddCircle, "Profile")
}

@Composable
fun InstagramBottomBar(
	selectedMenu: HomeBottomBarMenu = HomeBottomBarMenu.FEED,
	onMenuSelected: (HomeBottomBarMenu) -> Unit = {}
) {
	// Simple bottom bar visually matching standard Instagram
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.height(50.dp)
			.background(MaterialTheme.colorScheme.surface),
		horizontalArrangement = Arrangement.SpaceAround,
		verticalAlignment = Alignment.CenterVertically
	) {
		HomeBottomBarMenu.entries.forEach { menu ->
			when (menu) {
				HomeBottomBarMenu.PROFILE -> {
					IconButton(onClick = { onMenuSelected(menu) }) {
						// Profile Icon (usually user avatar)
						AsyncImage(
							model = "https://i.Pravatar.cc/150?img=60",
							contentDescription = "Profile",
							modifier = Modifier.size(28.dp)
								.clip(CircleShape)
								.border(1.dp, Color.Gray, CircleShape)
						)
					}
				}

				else -> IconButton(onClick = { onMenuSelected(menu) }) {
					Icon(
						imageVector = menu.icon,
						contentDescription = menu.label,
						modifier = Modifier.size(28.dp)
					)
				}
			}
		}
	}
}



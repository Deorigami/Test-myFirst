package app.tktn.attendees_check.screen.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import app.tktn.attendees_check.navigation.NavDestinations
import app.tktn.core_feature.base.BaseScreen
import app.tktn.core_feature.navigation.LocalNavStack
import kotlinx.coroutines.delay

object SplashScreen : BaseScreen() {
    @Composable
    override fun ComposeContent() {
        val navStack = LocalNavStack.current
		LaunchedEffect(Unit) {
			delay(2000)
			navStack.clear()
			navStack.add(NavDestinations.NewsHome)
		}

		Box(
			modifier = Modifier.Companion.fillMaxSize(),
			contentAlignment = Alignment.Companion.Center
		) {
			Text(
				text = "News App",
				style = MaterialTheme.typography.headlineLarge,
				fontWeight = FontWeight.Companion.Bold,
				color = MaterialTheme.colorScheme.primary
			)
		}
    }
}
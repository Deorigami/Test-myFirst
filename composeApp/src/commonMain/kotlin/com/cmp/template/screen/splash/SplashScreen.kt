package com.cmp.template.screen.splash
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.cmp.template.navigation.AppModuleNavigation
import com.cmp.template.core_feature.base.BaseScreen
import kotlinx.coroutines.delay
import org.koin.compose.koinInject
import kotlin.time.Duration.Companion.seconds
object SplashScreen : BaseScreen() {
    @Composable
    override fun ComposeContent() {
        val appNavigation = koinInject<AppModuleNavigation>()
        LaunchedEffect(Unit) {
            delay(1.seconds)
            appNavigation.navigateToHome()
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CMP Template",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

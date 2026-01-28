package app.tktn.feature_dashboard.landing

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import app.tktn.core_feature.base.BaseScreen
import kotlinx.serialization.Serializable

@Serializable
object DashboardLandingScreen : BaseScreen() {
    override val pageName: String?
        get() = this::class.simpleName

    @Composable
    override fun ComposeContent() {

    }
}





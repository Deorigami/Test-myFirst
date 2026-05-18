package com.cmp.template.feature_auth.open_store

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cmp.template.core_feature.base.BaseScreen
import com.cmp.template.feature_auth.login.rememberChromeLauncher
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf


@Serializable
object OpenStoreScreen : BaseScreen() {
	override val pageName: String?
		get() = this::class.simpleName

	@Composable
	override fun ComposeContent() {
		OpenStoreScreenContent()
	}

}

@Composable
fun OpenStoreScreenContent() {
	val browserTab = rememberChromeLauncher()
	val viewModel = koinViewModel<OpenStoreScreenModel> { parametersOf(browserTab) }
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(
			16.dp,
			Alignment.CenterVertically
		)
	) {
		Text("Store Currently Closed")
		Text("Awaiting authorized activation by Store IC")
		Button(onClick = { viewModel.startSingpassAuthentication() }) {
			Text("Open store")
		}
	}
}



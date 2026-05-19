package com.cmp.template.feature_auth.open_store

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cmp.template.components.IcosButton
import com.cmp.template.core_feature.base.BaseScreen
import com.cmp.template.feature_auth.login.rememberChromeLauncher
import com.cmp.template.theme.Color8F93A2
import com.cmp.template.theme.rememberIcosTextStyles
import dev.theolm.rinku.compose.ext.DeepLinkListener
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
	val viewModel =
		koinViewModel<OpenStoreScreenModel> { parametersOf(browserTab) }
	val ts = rememberIcosTextStyles()
	DeepLinkListener {
		viewModel.onSingpassDeeplinkReceived(it)
	}
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(
			24.dp,
			Alignment.CenterVertically
		)
	) {
		Text("Store Currently Closed", style = ts.Bold38)
		Text(
			"Awaiting authorized activation by Store IC",
			style = ts.Regular32.copy(Color8F93A2)
		)
		IcosButton(
			onClick = { viewModel.startSingpassAuthentication() },
			label = "Open store",
			modifier = Modifier.padding(top = 32.dp),
			isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value
		)
	}
}



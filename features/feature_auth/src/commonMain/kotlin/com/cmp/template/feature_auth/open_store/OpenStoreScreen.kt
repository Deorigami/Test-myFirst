package com.cmp.template.feature_auth.open_store

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cmp.template.components.IcosButton
import com.cmp.template.components.IcosOutlinedButton
import com.cmp.template.core_feature.base.BaseScreen
import com.cmp.template.feature_auth.login.rememberChromeLauncher
import com.cmp.template.theme.Color8F93A2
import com.cmp.template.theme.IcosTextStyles
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
	val viewModel = koinViewModel<OpenStoreScreenModel> {
		parametersOf(browserTab)
	}
	val state by viewModel.state.collectAsStateWithLifecycle()
	val ts = rememberIcosTextStyles()
	DeepLinkListener {
		viewModel.onSingpassDeeplinkReceived(it)
	}
	if (state.accessStatus == null) MainStoreContent(
		viewModel,
		ts,
		viewModel.isLoading.collectAsStateWithLifecycle().value
	)
	else AccessStatus(
		state.accessStatus == true,
		state.accessStatusCountdown,
		viewModel
	)
}

@Composable
private fun AccessStatus(
	accessStatus: Boolean,
	accessCountdown: Int,
	event: OpenStoreScreenEvent
) {
	val ts = rememberIcosTextStyles()
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
	) {
		Text(
			text = if (accessStatus) "Access Approved" else "Access Denied",
			style = ts.Bold38
		)
		Text(
			if (accessStatus) "Proceed through the turnstile" else "Please approach the Store Manager for assistance.",
			style = ts.Regular32.copy(Color8F93A2)
		)
		IcosOutlinedButton(
			onClick = { event },
			label = "Returning to home ($accessCountdown)",
			modifier = Modifier.padding(top = 32.dp),
			isLoading = false
		)
	}
}

@Composable
private fun MainStoreContent(
	event: OpenStoreScreenEvent,
	ts: IcosTextStyles,
	isLoading: Boolean
) {
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
			onClick = { event.startSingpassAuthentication() },
			label = "Open store",
			modifier = Modifier.padding(top = 32.dp),
			isLoading = isLoading
		)
	}
}



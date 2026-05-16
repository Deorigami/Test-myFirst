package com.cmp.template.feature_auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cmp.template.components.SingpassFooter
import com.cmp.template.core_feature.base.BaseScreen
import com.cmp.template.theme.AppBlue
import com.cmp.template.theme.SingpassRed
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
object SingpassLoginScreen : BaseScreen() {
    @Composable
    override fun ComposeContent() {
        val viewModel = koinViewModel<SingpassLoginScreenModel>()
        val state     by viewModel.state.collectAsState()
        val launcher  = rememberChromeLauncher()

        SingpassLoginContent(
            state    = state,
            onEvent  = viewModel::onEvent,
            onLaunch = { authUrl -> launcher.launch(authUrl) },
        )
    }
}

@Composable
private fun SingpassLoginContent(
    state: SingpassLoginScreenState,
    onEvent: (SingpassLoginScreenEvent) -> Unit,
    onLaunch: (String) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 360.dp)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            SingpassFooter(
                modifier = Modifier.fillMaxWidth(0.6f),
            )

            Spacer(Modifier.height(32.dp))

            Text(
                text = "Login with Singpass to continue",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(Modifier.height(24.dp))

            when {
                state.isLoading -> CircularProgressIndicator(
                    modifier = Modifier.size(36.dp),
                    color = SingpassRed,
                    strokeWidth = 3.dp,
                )

                state.error != null -> {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(Modifier.height(12.dp))
                    TextButton(onClick = { onEvent(SingpassLoginScreenEvent.Retry) }) {
                        Text("Retry")
                    }
                }

                else -> Button(
                    onClick = { state.authUrl?.let(onLaunch) },
                    enabled = state.authUrl != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AppBlue),
                ) {
                    Text(
                        text = "Open Singpass",
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        }
    }
}


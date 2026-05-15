package com.cmp.template.feature_sample.landing
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cmp.template.components.SampleCard
import com.cmp.template.core_feature.base.BaseScreen
import org.koin.compose.viewmodel.koinViewModel
import kotlinx.serialization.Serializable
@Serializable
object SampleScreen : BaseScreen() {
    @Composable
    override fun ComposeContent() {
        val viewModel = koinViewModel<SampleScreenModel>()
        val state by viewModel.state.collectAsState()
        SampleScreenContent(
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SampleScreenContent(
    state: SampleScreenState,
    onEvent: (SampleScreenEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Sample Feature") })
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.isLoading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
                state.error != null -> Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = state.error, color = MaterialTheme.colorScheme.error)
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { onEvent(SampleScreenEvent.Refresh) }) {
                        Text("Retry")
                    }
                }
                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.items) { item ->
                        SampleCard(
                            title = item.title,
                            description = item.description,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

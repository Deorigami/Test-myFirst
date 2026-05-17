package com.cmp.template.feature_auth.login

import androidx.lifecycle.viewModelScope
import com.cmp.template.core_feature.base.BaseScreenModel
import com.cmp.template.core_service.model.StatefulResult
import com.cmp.template.service_auth.domain.usecase.GetSingpassAuthUrlUseCase
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import kotlin.uuid.ExperimentalUuidApi

@Factory
class SingpassLoginScreenModel(
    private val chromeTabLauncher: ChromeTabLauncher,
    private val getSingpassAuthUrlUseCase: GetSingpassAuthUrlUseCase,
) : BaseScreenModel<SingpassLoginScreenState>(SingpassLoginScreenState()) {

    fun onEvent(event: SingpassLoginScreenEvent) {
        when (event) {
            is SingpassLoginScreenEvent.FetchAuthUrl -> fetchAuthUrl()
            is SingpassLoginScreenEvent.Retry        -> fetchAuthUrl()
        }
    }

    @OptIn(ExperimentalUuidApi::class)
	private fun fetchAuthUrl() {
        updateState { copy(isLoading = true, error = null) }
        viewModelScope.launch {
            // ngrok tunnel to local Supabase (real HTTPS domain)
            val baseUrl = "https://2468-2402-8780-1023-7260-bd14-8585-92-92c4.ngrok-free.app"
            val state = kotlin.uuid.Uuid.random().toString()
            val mockUrl = "$baseUrl/functions/v1/singpass-mock?state=$state"
            chromeTabLauncher.launch(mockUrl)
            updateState { copy(isLoading = false) }
        }
    }
}


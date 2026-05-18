package com.cmp.template.feature_auth.login

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import co.touchlab.kermit.Logger
import com.cmp.template.core_feature.base.BaseScreenModel
import com.cmp.template.service_auth.domain.usecase.ExchangeSingpassTokenUseCase
import com.cmp.template.service_auth.domain.usecase.GetSingpassAuthUrlUseCase
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import kotlin.uuid.ExperimentalUuidApi

@Factory
class SingpassLoginScreenModel(
    private val chromeTabLauncher: ChromeTabLauncher,
    private val getSingpassAuthUrlUseCase: GetSingpassAuthUrlUseCase,
    private val exchangeSingpassTokenUseCase: ExchangeSingpassTokenUseCase,
) : BaseScreenModel<SingpassLoginScreenState>(SingpassLoginScreenState()), SingpassLoginScreenEvent {

    private fun onFetchAuthUrl() {
        updateState { copy(isLoading = true, error = null) }
        viewModelScope.launch {
            @OptIn(ExperimentalUuidApi::class)
            val state   = kotlin.uuid.Uuid.random().toString()
            val baseUrl = "https://3c90-2402-8780-1023-7260-651e-2ecd-e284-9be8.ngrok-free.app"
            chromeTabLauncher.launch("$baseUrl/functions/v1/singpass-mock?state=$state")
            updateState { copy(isLoading = false) }
        }
    }

    override fun onRetry() = onFetchAuthUrl()
    override fun onDeeplinkReceived(parameters: Map<String, String>) {
        val code  = parameters["code"] ?: run {
            Logger.e { "Authorization code missing in deep link parameters" }
            updateState { copy(error = "Authorization code missing", isLoading = false) }
            return
        }
        val state  = parameters["state"] ?: run {
            Logger.e { "Authorization state missing in deep link parameters" }
            updateState { copy(error = "Authorization code missing", isLoading = false) }
            return
        }
        exchangeSingpassTokenUseCase.execute(viewModelScope, code) { result ->

        }
    }
}

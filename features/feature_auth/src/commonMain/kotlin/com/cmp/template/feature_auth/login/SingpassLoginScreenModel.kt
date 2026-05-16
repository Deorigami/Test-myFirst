package com.cmp.template.feature_auth.login

import androidx.lifecycle.viewModelScope
import com.cmp.template.core_feature.base.BaseScreenModel
import com.cmp.template.core_service.model.StatefulResult
import com.cmp.template.service_auth.domain.usecase.GetSingpassAuthUrlUseCase
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

@Factory
class SingpassLoginScreenModel(
    private val getSingpassAuthUrlUseCase: GetSingpassAuthUrlUseCase,
) : BaseScreenModel<SingpassLoginScreenState>(SingpassLoginScreenState()) {

    init {
        fetchAuthUrl()
    }

    fun onEvent(event: SingpassLoginScreenEvent) {
        when (event) {
            is SingpassLoginScreenEvent.FetchAuthUrl -> fetchAuthUrl()
            is SingpassLoginScreenEvent.Retry        -> fetchAuthUrl()
        }
    }

    private fun fetchAuthUrl() {
        updateState { copy(isLoading = true, error = null) }
        viewModelScope.launch {
            getSingpassAuthUrlUseCase.execute(Unit) { result ->
                when (result) {
                    is StatefulResult.Success -> updateState {
                        copy(authUrl = result.data.authUrl, isLoading = false)
                    }
                    is StatefulResult.Error   -> updateState {
                        copy(error = result.error.message, isLoading = false)
                    }
                    is StatefulResult.Loading -> updateState { copy(isLoading = true) }
                }
            }
        }
    }
}


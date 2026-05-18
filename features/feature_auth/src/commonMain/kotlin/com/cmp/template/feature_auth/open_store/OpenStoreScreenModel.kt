package com.cmp.template.feature_auth.open_store

import androidx.lifecycle.viewModelScope
import com.cmp.template.core_feature.base.BaseScreenModel
import com.cmp.template.feature_auth.login.ChromeTabLauncher
import com.cmp.template.service_auth.domain.usecase.ExchangeSingpassTokenUseCase
import com.cmp.template.service_auth.domain.usecase.GetSingpassAuthUrlUseCase
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import kotlin.uuid.ExperimentalUuidApi

@KoinViewModel
class OpenStoreScreenModel(
	private val chromeTabLauncher: ChromeTabLauncher,
	private val getSingpassAuthUrlUseCase: GetSingpassAuthUrlUseCase,
	private val exchangeSingpassTokenUseCase: ExchangeSingpassTokenUseCase
) : BaseScreenModel<OpenStoreScreenState>(OpenStoreScreenState()),
	OpenStoreScreenEvent
{
	override fun startSingpassAuthentication() {
		getSingpassAuthUrlUseCase.execute(viewModelScope, Unit){
			launchSingpassLoginTab()
		}
	}

	private fun launchSingpassLoginTab(){
		viewModelScope.launch {
			@OptIn(ExperimentalUuidApi::class)
			val state   = kotlin.uuid.Uuid.random().toString()
			val baseUrl = "https://3c90-2402-8780-1023-7260-651e-2ecd-e284-9be8.ngrok-free.app"
			chromeTabLauncher.launch("$baseUrl/functions/v1/singpass-mock?state=$state")
			updateState { copy(isLoading = false) }
		}
	}
}
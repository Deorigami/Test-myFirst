package com.cmp.template.feature_auth.open_store

import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.cmp.template.core_feature.base.BaseScreenModel
import com.cmp.template.core_service.base.BaseUseCase
import com.cmp.template.feature_auth.di.FeatureAuthNavigation
import com.cmp.template.feature_auth.login.chromeLauncher
import com.cmp.template.service_auth.domain.usecase.ExchangeSingpassTokenUseCase
import com.cmp.template.service_auth.domain.usecase.GetSingpassAuthUrlUseCase
import dev.theolm.rinku.DeepLink
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import kotlin.time.Duration.Companion.seconds
import kotlin.uuid.ExperimentalUuidApi

@KoinViewModel
class OpenStoreScreenModel(
	private val getSingpassAuthUrlUseCase: GetSingpassAuthUrlUseCase,
	private val exchangeSingpassTokenUseCase: ExchangeSingpassTokenUseCase,
	private val nav: FeatureAuthNavigation
) : BaseScreenModel<OpenStoreScreenState>(OpenStoreScreenState()),
	OpenStoreScreenEvent
{
	private var countDownJob: Job? = null
	override val registerLoadingListener: List<BaseUseCase<*, *>>
		get() = listOf(
			getSingpassAuthUrlUseCase,
			exchangeSingpassTokenUseCase
		)

	override fun startSingpassAuthentication() {
		getSingpassAuthUrlUseCase.execute(viewModelScope, Unit) {
			launchSingpassLoginTab()
		}
	}

	override fun onSingpassDeeplinkReceived(deepLink: DeepLink) {
		val code = deepLink.parameters["code"] ?: run {
			return
		}
		val state = deepLink.parameters["state"] ?: run {
			return
		}
		exchangeSingpassTokenUseCase.execute(viewModelScope, code) {
			updateState {
				copy(
					accessStatus = true,
					accessStatusCountdown = 5
				)
			}
			startCountdown()
		}
	}

	override fun accessStatusButtonAction() {
		val status = state.value.accessStatus
		updateState { copy(
			accessStatus = null
		) }
		stopCountdownJob()
		Logger.d("ANGGATAG"){"Access Status: $status"}
		when(status){
			true -> nav.navigateToHome()
			false -> Unit
			else -> Unit
		}
	}

	private fun startCountdown() {
		countDownJob = viewModelScope.launch {
			delay(1.seconds)
			while (state.value.accessStatusCountdown >= 0) {
				if (state.value.accessStatusCountdown > 0) {
					updateState {
						copy(accessStatusCountdown = accessStatusCountdown.minus(1))
					}
				} else {
					stopCountdownJob()
					accessStatusButtonAction()
					break
				}
				delay(1.seconds)
			}
		}
	}

	private fun stopCountdownJob() {
		countDownJob?.cancel()
		countDownJob?.cancelChildren()
		countDownJob = null
		updateState { copy(
			accessStatusCountdown = 0
		) }
	}

	private fun launchSingpassLoginTab() {
		viewModelScope.launch {
			@OptIn(ExperimentalUuidApi::class)
			val state = kotlin.uuid.Uuid.random().toString()
			val baseUrl =
				"https://amoeba-current-iguana.ngrok-free.app"
			chromeLauncher().launch("$baseUrl/functions/v1/singpass-mock?state=$state")
			updateState {
				copy(isLoading = false)
			}
		}
	}
}
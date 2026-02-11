package app.tktn.feature_feed.landing.composable.stories

import app.tktn.core_feature.base.BaseScreenModel
import app.tktn.core_feature.util.executeInViewModel
import app.tktn.core_service.model.StatefulResult.Companion.onError
import app.tktn.core_service.model.StatefulResult.Companion.onSuccess
import app.tktn.service_feed.domain.usecase.GetStoriesUseCase
import co.touchlab.kermit.Logger
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class StoriesModel(
	private val getStoriesUseCase: GetStoriesUseCase
) : BaseScreenModel<StoriesState, StoriesEvent>(StoriesState()) {

	init {
		onEvent(StoriesEvent.RefreshData)
	}

	override fun onEvent(event: StoriesEvent) {
		when (event) {
			StoriesEvent.RefreshData -> refreshData()
		}
	}

	private fun refreshData() {
		getStoriesUseCase.executeInViewModel(Unit) {
			it.onSuccess { result ->
				updateState {
					it.copy(
						stories = result
					)
				}
			}
			it.onError {
				Logger.e("RefreshData"){ it?.message ?: "" }
			}
		}
	}
}
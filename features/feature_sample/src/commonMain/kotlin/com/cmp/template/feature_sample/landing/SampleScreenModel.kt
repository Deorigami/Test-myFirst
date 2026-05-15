package com.cmp.template.feature_sample.landing
import androidx.lifecycle.viewModelScope
import com.cmp.template.core_feature.base.BaseScreenModel
import com.cmp.template.service_sample.domain.usecase.GetSampleItemsUseCase
import com.cmp.template.core_service.model.StatefulResult
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
@Factory
class SampleScreenModel(
    private val getSampleItemsUseCase: GetSampleItemsUseCase
) : BaseScreenModel<SampleScreenState>(SampleScreenState()) {
    init {
        loadItems()
    }
    fun onEvent(event: SampleScreenEvent) {
        when (event) {
            is SampleScreenEvent.LoadItems -> loadItems()
            is SampleScreenEvent.Refresh -> loadItems()
        }
    }
    private fun loadItems() {
        updateState { copy(isLoading = true, error = null) }
        viewModelScope.launch {
            getSampleItemsUseCase.execute(Unit) { result ->
                when (result) {
                    is StatefulResult.Success -> updateState {
                        copy(items = result.data, isLoading = false)
                    }
                    is StatefulResult.Error -> updateState {
                        copy(error = result.error.message, isLoading = false)
                    }
                    is StatefulResult.Loading -> updateState { copy(isLoading = true) }
                }
            }
        }
    }
}

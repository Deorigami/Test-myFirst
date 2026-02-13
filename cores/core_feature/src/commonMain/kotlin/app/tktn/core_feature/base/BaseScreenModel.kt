package app.tktn.core_feature.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * A simplified BaseScreenModel following Unidirectional Data Flow.
 * Removed the coupling with BaseUseCase and forced registerLoadingListener.
 */
abstract class BaseScreenModel<UiState, Event>(
    initialState: UiState
) : ViewModel() {
    protected val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()
    
    protected val currentState get() = _uiState.value

    abstract fun onEvent(event: Event)
    
    protected fun updateState(reducer: (UiState) -> UiState) {
        _uiState.update { reducer(it) }
    }
}
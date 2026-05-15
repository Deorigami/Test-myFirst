package com.cmp.template.core_feature.base
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
abstract class BaseScreenModel<S>(initialState: S) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()
    protected fun updateState(block: S.() -> S) {
        _state.update { it.block() }
    }
}

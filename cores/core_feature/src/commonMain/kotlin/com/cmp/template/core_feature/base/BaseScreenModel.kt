package com.cmp.template.core_feature.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmp.template.core_service.base.BaseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

abstract class BaseScreenModel<S>(initialState: S) : ViewModel() {
    protected open val registerLoadingListener : List<BaseUseCase<*, *>> = emptyList()
    val isLoading by lazy {
        combine(registerLoadingListener.map { it.isLoading }) {
            it.any { it }
        }.stateIn(viewModelScope, SharingStarted.Lazily, false)
    }
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    protected fun updateState(block: S.() -> S) {
        _state.update { it.block() }
    }
}

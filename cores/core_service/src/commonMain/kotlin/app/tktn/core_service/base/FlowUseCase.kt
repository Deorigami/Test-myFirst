package app.tktn.core_service.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

abstract class FlowUseCase<in P, out R> {
    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    abstract fun build(param: P): Flow<R>

    operator fun invoke(param: P): Flow<R> = build(param)
        .onStart { _isLoading.update { true } }
        .onCompletion { _isLoading.update { false } }
}
package com.cmp.template.core_service.base
import com.cmp.template.core_service.model.DomainResult
import com.cmp.template.core_service.model.StatefulResult
import com.cmp.template.core_service.model.toStateful
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
abstract class BaseUseCase<P, R> {
    protected abstract val default: R
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    var result: R? = null
    abstract suspend fun build(param: P): DomainResult<R>
    fun execute(
        coroutineScope: CoroutineScope,
        param: P,
        onResult: (StatefulResult<R>) -> Unit = {}
    ) {
        _isLoading.update { true }
        coroutineScope.launch {
            val domainResult = runCatching { build(param) }
                .getOrElse { e -> DomainResult.Error(com.cmp.template.core_service.model.CoreError(message = e.message ?: "Unknown")) }
            val stateful = domainResult.toStateful()
            result = (stateful as? StatefulResult.Success)?.data
            _isLoading.update { false }
            onResult(stateful)
        }
    }
    suspend fun execute(param: P, onResult: (StatefulResult<R>) -> Unit = {}) {
        _isLoading.update { true }
        val domainResult = runCatching { build(param) }
            .getOrElse { e -> DomainResult.Error(com.cmp.template.core_service.model.CoreError(message = e.message ?: "Unknown")) }
        val stateful = domainResult.toStateful()
        result = (stateful as? StatefulResult.Success)?.data
        _isLoading.update { false }
        onResult(stateful)
    }
}

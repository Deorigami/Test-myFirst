package app.tktn.core_service.base

import app.tktn.core_service.extension.either
import app.tktn.core_service.extension.toResult
import app.tktn.core_service.model.DomainResult
import app.tktn.core_service.model.StatefulResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseUseCase<P, R> {
    protected abstract val default: R
    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
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
			onResult(
				either {
					build(param)
				}.toResult(default).also {
					_isLoading.update { false }
				}.let {
					result = (it as? StatefulResult.Success)?.data
					it
				}
			)
		}
    }

    suspend fun execute(
        param: P,
        onResult: (StatefulResult<R>) -> Unit = {}
    ) {
        _isLoading.update { true }
        onResult(
            either {
                build(param)
            }.toResult(default).also {
                _isLoading.update { false }
            }.let {
                result = (it as? StatefulResult.Success)?.data
                it
            }
        )
    }
}
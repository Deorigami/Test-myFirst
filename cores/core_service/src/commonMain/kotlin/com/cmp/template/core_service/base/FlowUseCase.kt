package com.cmp.template.core_service.base
import com.cmp.template.core_service.model.DomainResult
import com.cmp.template.core_service.model.StatefulResult
import com.cmp.template.core_service.model.toStateful
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
abstract class FlowUseCase<P, R> {
    abstract fun build(param: P): Flow<DomainResult<R>>
    fun execute(param: P): Flow<StatefulResult<R>> =
        build(param)
            .map { it.toStateful() }
            .catch { e ->
                emit(StatefulResult.Error(com.cmp.template.core_service.model.CoreError(message = e.message ?: "Unknown")))
            }
}

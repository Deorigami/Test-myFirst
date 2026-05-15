package com.cmp.template.core_service.extension
import com.cmp.template.core_service.model.CoreError
import com.cmp.template.core_service.model.DomainResult
suspend fun <T> either(block: suspend () -> DomainResult<T>): DomainResult<T> {
    return runCatching { block() }
        .getOrElse { e -> DomainResult.Error(CoreError(message = e.message ?: "Unknown error")) }
}

package com.cmp.template.core_service.model
sealed interface DomainResult<out T> {
    data class Success<T>(val data: T) : DomainResult<T>
    data class Error(val error: CoreError) : DomainResult<Nothing>
}

package com.cmp.template.core_service.model
sealed interface StatefulResult<out T> {
    data object Loading : StatefulResult<Nothing>
    data class Success<T>(val data: T) : StatefulResult<T>
    data class Error(val error: CoreError) : StatefulResult<Nothing>
}
fun <T> DomainResult<T>.toStateful(): StatefulResult<T> = when (this) {
    is DomainResult.Success -> StatefulResult.Success(data)
    is DomainResult.Error -> StatefulResult.Error(error)
}

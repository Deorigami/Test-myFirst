package com.cmp.template.core_feature.util
import com.cmp.template.core_service.model.CoreError
import com.cmp.template.core_service.model.StatefulResult
fun <T> StatefulResult<T>.onSuccess(block: (T) -> Unit): StatefulResult<T> {
    if (this is StatefulResult.Success) block(data)
    return this
}
fun <T> StatefulResult<T>.onError(block: (CoreError) -> Unit): StatefulResult<T> {
    if (this is StatefulResult.Error) block(error)
    return this
}
fun <T> StatefulResult<T>.onLoading(block: () -> Unit): StatefulResult<T> {
    if (this is StatefulResult.Loading) block()
    return this
}

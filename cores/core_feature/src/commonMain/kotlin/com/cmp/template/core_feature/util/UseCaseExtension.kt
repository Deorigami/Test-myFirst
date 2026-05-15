package com.cmp.template.core_feature.util
import com.cmp.template.core_service.model.StatefulResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
fun <T> MutableStateFlow<StatefulResult<T>>.updateResult(result: StatefulResult<T>) {
    update { result }
}

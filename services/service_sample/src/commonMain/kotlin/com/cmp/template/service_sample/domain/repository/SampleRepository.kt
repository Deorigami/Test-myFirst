package com.cmp.template.service_sample.domain.repository
import com.cmp.template.core_service.model.DomainResult
import com.cmp.template.service_sample.domain.entity.SampleItem
interface SampleRepository {
    suspend fun getItems(): DomainResult<List<SampleItem>>
}

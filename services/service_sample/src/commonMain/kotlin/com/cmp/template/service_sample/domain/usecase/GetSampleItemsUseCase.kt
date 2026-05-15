package com.cmp.template.service_sample.domain.usecase
import com.cmp.template.core_service.base.BaseUseCase
import com.cmp.template.core_service.model.DomainResult
import com.cmp.template.service_sample.domain.entity.SampleItem
import com.cmp.template.service_sample.domain.repository.SampleRepository
import org.koin.core.annotation.Factory
@Factory
class GetSampleItemsUseCase(
    private val repository: SampleRepository
) : BaseUseCase<Unit, List<SampleItem>>() {
    override val default: List<SampleItem> = emptyList()
    override suspend fun build(param: Unit): DomainResult<List<SampleItem>> {
        return repository.getItems()
    }
}

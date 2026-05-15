package com.cmp.template.service_sample.data.repository
import com.cmp.template.core_service.model.CoreError
import com.cmp.template.core_service.model.DomainResult
import com.cmp.template.service_sample.data.remote.SampleApi
import com.cmp.template.service_sample.domain.entity.SampleItem
import com.cmp.template.service_sample.domain.repository.SampleRepository
import org.koin.core.annotation.Single
@Single
class SampleRepositoryImpl(
    private val api: SampleApi
) : SampleRepository {
    override suspend fun getItems(): DomainResult<List<SampleItem>> {
        return runCatching {
            val response = api.getItems()
            val items = response.map { dto ->
                SampleItem(
                    id = dto.id ?: 0,
                    title = dto.title ?: "",
                    description = dto.body ?: ""
                )
            }
            DomainResult.Success(items)
        }.getOrElse { e ->
            DomainResult.Error(CoreError(message = e.message ?: "Unknown error"))
        }
    }
}

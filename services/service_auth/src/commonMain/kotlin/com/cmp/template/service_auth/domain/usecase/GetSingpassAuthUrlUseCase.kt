package com.cmp.template.service_auth.domain.usecase

import com.cmp.template.core_service.base.BaseUseCase
import com.cmp.template.core_service.model.DomainResult
import com.cmp.template.service_auth.domain.entity.SingpassAuthUrl
import com.cmp.template.service_auth.domain.repository.SingpassRepository
import org.koin.core.annotation.Factory

@Factory
class GetSingpassAuthUrlUseCase(
    private val repository: SingpassRepository,
) : BaseUseCase<Unit, SingpassAuthUrl>() {

    override val default: SingpassAuthUrl = SingpassAuthUrl(authUrl = "", state = "")

    override suspend fun build(param: Unit): DomainResult<SingpassAuthUrl> {
        return repository.getAuthUrl()
    }
}


package com.cmp.template.service_auth.data.repository

import com.cmp.template.core_service.model.CoreError
import com.cmp.template.core_service.model.DomainResult
import com.cmp.template.service_auth.data.remote.SingpassApi
import com.cmp.template.service_auth.domain.entity.SingpassAuthUrl
import com.cmp.template.service_auth.domain.repository.SingpassRepository
import org.koin.core.annotation.Single

@Single
class SingpassRepositoryImpl(
    private val api: SingpassApi,
) : SingpassRepository {

    override suspend fun getAuthUrl(): DomainResult<SingpassAuthUrl> {
        return runCatching {
            val response = api.getAuthUrl()
            DomainResult.Success(
                SingpassAuthUrl(
                    authUrl = response.authUrl ?: error("Missing auth_url in response"),
                    state   = response.state   ?: "",
                )
            )
        }.getOrElse { e ->
            DomainResult.Error(CoreError(message = e.message ?: "Unknown error"))
        }
    }
}


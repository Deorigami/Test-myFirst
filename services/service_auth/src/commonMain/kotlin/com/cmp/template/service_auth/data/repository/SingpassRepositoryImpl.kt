package com.cmp.template.service_auth.data.repository

import com.cmp.template.core_service.model.CoreError
import com.cmp.template.core_service.model.DomainResult
import com.cmp.template.service_auth.data.remote.SingpassApi
import com.cmp.template.service_auth.data.remote.dto.SingpassTokenRequest
import com.cmp.template.service_auth.domain.entity.SingpassAuthUrl
import com.cmp.template.service_auth.domain.entity.SingpassUser
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

    override suspend fun exchangeToken(code: String): DomainResult<SingpassUser> {
        return runCatching {
            val response = api.exchangeToken(SingpassTokenRequest(code = code))
            val claims = response.decoded
                ?: error("Missing decoded claims in token response")
            val attrs = claims.subAttributes
                ?: error("Missing sub_attributes in token claims")

            DomainResult.Success(
                SingpassUser(
                    sub            = claims.subject ?: "",
                    identityNumber = attrs.identityNumber ?: error("Missing identity_number"),
                    name           = attrs.name ?: "",
                    email          = attrs.email ?: "",
                    mobileNo       = attrs.mobileNo ?: "",
                    identityCoi    = attrs.identityCoi ?: "SG",
                    accountType    = attrs.accountType ?: "standard",
                )
            )
        }.getOrElse { e ->
            DomainResult.Error(CoreError(message = e.message ?: "Unknown error"))
        }
    }
}

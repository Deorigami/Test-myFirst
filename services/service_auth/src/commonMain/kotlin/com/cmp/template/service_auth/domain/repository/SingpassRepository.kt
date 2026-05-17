package com.cmp.template.service_auth.domain.repository

import com.cmp.template.core_service.model.DomainResult
import com.cmp.template.service_auth.domain.entity.SingpassAuthUrl
import com.cmp.template.service_auth.domain.entity.SingpassUser

interface SingpassRepository {
    suspend fun getAuthUrl(): DomainResult<SingpassAuthUrl>

    /** Exchanges an authorization [code] for a decoded Singpass user profile. */
    suspend fun exchangeToken(code: String): DomainResult<SingpassUser>
}

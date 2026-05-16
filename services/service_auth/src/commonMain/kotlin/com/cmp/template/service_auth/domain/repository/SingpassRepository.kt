package com.cmp.template.service_auth.domain.repository

import com.cmp.template.core_service.model.DomainResult
import com.cmp.template.service_auth.domain.entity.SingpassAuthUrl

interface SingpassRepository {
    suspend fun getAuthUrl(): DomainResult<SingpassAuthUrl>
}


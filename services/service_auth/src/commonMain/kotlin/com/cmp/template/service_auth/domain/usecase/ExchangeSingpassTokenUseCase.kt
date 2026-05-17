package com.cmp.template.service_auth.domain.usecase

import com.cmp.template.core_service.base.BaseUseCase
import com.cmp.template.core_service.model.DomainResult
import com.cmp.template.service_auth.domain.entity.SingpassUser
import com.cmp.template.service_auth.domain.repository.SingpassRepository
import org.koin.core.annotation.Factory

/**
 * Exchanges a Singpass authorization code for user profile data.
 *
 * @param param The authorization code received from the deep link callback.
 * @return [SingpassUser] with the authenticated user's profile.
 */
@Factory
class ExchangeSingpassTokenUseCase(
    private val repository: SingpassRepository,
) : BaseUseCase<String, SingpassUser>() {

    override val default: SingpassUser = SingpassUser(
        sub = "",
        identityNumber = "",
        name = "",
        email = "",
        mobileNo = "",
    )

    override suspend fun build(param: String): DomainResult<SingpassUser> {
        return repository.exchangeToken(param)
    }
}

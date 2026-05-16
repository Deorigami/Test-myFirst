package com.cmp.template.service_auth

import com.cmp.template.service_auth.data.remote.SingpassApi
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.cmp.template.service_auth")
class ServiceAuthModule {
    @Single
    fun provideSingpassApi(ktorfit: Ktorfit): SingpassApi = ktorfit.create()
}


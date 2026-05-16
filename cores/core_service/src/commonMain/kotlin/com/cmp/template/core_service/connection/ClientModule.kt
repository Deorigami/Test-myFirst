package com.cmp.template.core_service.connection

import com.cmp.template.core_service.base.Constant
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.cmp.template")
class ServiceModule {
    @Single
    fun provideKtorfit(): Ktorfit = Ktorfit.Builder()
        .baseUrl(Constant.BASE_URL)
        .httpClient(buildHttpClient())
        .build()
}


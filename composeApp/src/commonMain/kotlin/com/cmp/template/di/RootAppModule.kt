package com.cmp.template.di

import com.cmp.template.core_feature.di.CoreFeatureModule
import com.cmp.template.core_service.connection.ServiceModule
import com.cmp.template.feature_auth.di.FeatureAuthModule
import com.cmp.template.feature_sample.di.FeatureSampleModule
import com.cmp.template.service_auth.ServiceAuthModule
import com.cmp.template.service_sample.ServiceSampleModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(
    includes = [
        ServiceModule::class,
        ServiceSampleModule::class,
        ServiceAuthModule::class,
        FeatureSampleModule::class,
        FeatureAuthModule::class,
        CoreFeatureModule::class,
    ]
)
@ComponentScan("com.cmp.template")
class RootAppModule

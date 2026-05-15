package com.cmp.template.di
import com.cmp.template.core_feature.di.CoreFeatureModule
import com.cmp.template.core_service.connection.ServiceModule
import com.cmp.template.feature_sample.di.FeatureSampleModule
import com.cmp.template.service_sample.ServiceSampleModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
@Module(
    includes = [
        ServiceModule::class,
        ServiceSampleModule::class,
        FeatureSampleModule::class,
        CoreFeatureModule::class
        // TODO: Include new feature/service modules here
    ]
)
@ComponentScan("com.cmp.template")
class RootAppModule

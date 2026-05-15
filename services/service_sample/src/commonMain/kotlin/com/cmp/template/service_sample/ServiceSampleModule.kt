package com.cmp.template.service_sample
import com.cmp.template.service_sample.data.remote.SampleApi
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
@Module
@ComponentScan("com.cmp.template.service_sample")
class ServiceSampleModule {
    @Single
    fun provideSampleApi(ktorfit: Ktorfit): SampleApi = ktorfit.create()
}

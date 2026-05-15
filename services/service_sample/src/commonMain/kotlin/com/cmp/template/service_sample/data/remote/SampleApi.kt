package com.cmp.template.service_sample.data.remote
import com.cmp.template.service_sample.data.remote.dto.SampleResponse
import de.jensklingenberg.ktorfit.http.GET
interface SampleApi {
    // TODO: Replace with your actual API endpoints
    @GET("posts")
    suspend fun getItems(): List<SampleResponse>
}

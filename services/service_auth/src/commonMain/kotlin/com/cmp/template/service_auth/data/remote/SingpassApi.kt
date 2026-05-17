package com.cmp.template.service_auth.data.remote

import com.cmp.template.service_auth.data.remote.dto.SingpassAuthUrlResponse
import com.cmp.template.service_auth.data.remote.dto.SingpassTokenRequest
import com.cmp.template.service_auth.data.remote.dto.SingpassTokenResponse
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Headers
import de.jensklingenberg.ktorfit.http.POST

interface SingpassApi {
    /** Fetches the Singpass OAuth authorisation URL from the backend. */
    @GET("singpass/auth-url")
    suspend fun getAuthUrl(): SingpassAuthUrlResponse

    /**
     * Exchanges a Singpass authorization code for an ID token.
     *
     * In the mock environment this hits `/functions/v1/singpass-token`.
     * In production this would call the real Singpass FAPI token endpoint
     * (proxied through your backend for client-secret safety).
     */
    @Headers("Content-Type: application/json")
    @POST("functions/v1/singpass-token")
    suspend fun exchangeToken(@Body request: SingpassTokenRequest): SingpassTokenResponse
}

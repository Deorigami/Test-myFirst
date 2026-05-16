package com.cmp.template.service_auth.data.remote

import com.cmp.template.service_auth.data.remote.dto.SingpassAuthUrlResponse
import de.jensklingenberg.ktorfit.http.GET

interface SingpassApi {
    /** Fetches the Singpass OAuth authorisation URL from the backend. */
    @GET("singpass/auth-url")
    suspend fun getAuthUrl(): SingpassAuthUrlResponse
}


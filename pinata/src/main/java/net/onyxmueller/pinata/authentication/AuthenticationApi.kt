package net.onyxmueller.pinata.authentication

import net.onyxmueller.pinata.PinataApiResponse
import net.onyxmueller.pinata.authentication.model.AuthTestResponse
import retrofit2.http.GET

interface AuthenticationApi {
    @GET("data/testAuthentication")
    suspend fun test(): PinataApiResponse<AuthTestResponse>
}

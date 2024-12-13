package net.onyxmueller.pinata.interceptors

import okhttp3.Interceptor
import okhttp3.Response

internal class AuthInterceptor(
    private val jwtToken: String,
    private val isUpload: Boolean = false,
    private val additionalHeaders: Map<String, String> = emptyMap(),
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val contentType = if (isUpload) "multipart/form-data" else "application/json"

        val requestBuilder =
            originalRequest.newBuilder()
                .apply {
                    if (!originalRequest.headers.names().contains("Content-Type")) {
                        addHeader("Content-Type", contentType)
                    }
                    if (!originalRequest.headers.names().contains("Authorization")) {
                        addHeader("Authorization", "Bearer $jwtToken")
                    }
                    for ((key, value) in additionalHeaders) {
                        addHeader(key, value)
                    }
                }

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}

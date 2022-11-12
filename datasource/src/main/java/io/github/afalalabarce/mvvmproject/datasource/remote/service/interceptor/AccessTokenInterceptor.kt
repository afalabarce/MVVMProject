package io.github.afalalabarce.mvvmproject.datasource.remote.service.interceptor

import io.github.afalalabarce.mvvmproject.datasource.remote.service.interfaces.AccessTokenProvider
import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(
    private val tokenProvider: AccessTokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider.token()
        val refreshToken = tokenProvider.refreshToken()
        // TODO Make actions for token refresh
        return if (token == "") {
            chain.proceed(chain.request())
        } else {
            val authenticatedRequest = chain.request()
                .newBuilder()
                .addHeader("Authorization", "bearer $token")
                .build()
            chain.proceed(authenticatedRequest)
        }
    }
}
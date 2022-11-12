package io.github.afalalabarce.mvvmproject.datasource.remote.service.interceptor

import io.github.afalalabarce.mvvmproject.datasource.remote.service.interfaces.AccessTokenProvider
import okhttp3.Interceptor
import okhttp3.Response

class UnauthorizedAccessInterceptor(
    private val tokenProvider: AccessTokenProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (response.code == 401 || response.code == 403) {
            tokenProvider.cleanToken()
        }
        return response
    }
}
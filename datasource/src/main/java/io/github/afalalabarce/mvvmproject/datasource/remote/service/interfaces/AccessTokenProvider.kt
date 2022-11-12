package io.github.afalalabarce.mvvmproject.datasource.remote.service.interfaces

interface AccessTokenProvider {
    fun token(): String
    fun refreshToken(): String
    fun cleanToken()
}
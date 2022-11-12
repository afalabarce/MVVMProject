package io.github.afalalabarce.mvvmproject.datasource.remote.service

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.github.afalalabarce.mvvmproject.datasource.remote.model.RemoteExampleEntity
import io.github.afalalabarce.mvvmproject.datasource.remote.service.interceptor.AccessTokenInterceptor
import io.github.afalalabarce.mvvmproject.datasource.remote.service.interceptor.UnauthorizedAccessInterceptor
import io.github.afalalabarce.mvvmproject.datasource.remote.service.interfaces.AccessTokenProvider
import io.github.afalalabarce.mvvmproject.model.serializer.GsonDateDeserializer
import io.github.afalalabarce.mvvmproject.model.serializer.GsonInstantDeserializer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import java.time.Instant
import java.util.*
import java.util.concurrent.TimeUnit

abstract class AppRemoteService {
    @GET("v1/remote")
    abstract suspend fun getRemoteEntity(): Response<List<RemoteExampleEntity>>

    companion object{
        const val BASE_DEBUG_API_URL = "https://url.debug.to.webservice"
        const val BASE_API_URL = "https://url.to.webservice"
        const val retrofitReadTimeOut = 60L
        const val retrofitWriteTimeOut = 60L

        fun makeGson(): Gson {
            return GsonBuilder()
                .setLenient()
                .registerTypeAdapter(Date::class.java, GsonDateDeserializer())
                .registerTypeAdapter(Instant::class.java, GsonInstantDeserializer())
                .setPrettyPrinting()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
        }

        private fun makeLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }

        fun makeOkHttpClient(accessTokenProvider: AccessTokenProvider): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(makeLoggingInterceptor())
            .addInterceptor(AccessTokenInterceptor(accessTokenProvider))
            .addInterceptor(UnauthorizedAccessInterceptor(accessTokenProvider))
            .connectTimeout(retrofitWriteTimeOut, TimeUnit.SECONDS)
            .readTimeout(retrofitReadTimeOut, TimeUnit.SECONDS)
            .build()
    }
}
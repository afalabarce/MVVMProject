package io.github.afalalabarce.mvvmproject.datasource.di

import android.content.Context
import androidx.room.Room
import io.github.afalalabarce.mvvmproject.datasource.BuildConfig
import io.github.afalalabarce.mvvmproject.datasource.cache.db.AppDatabase
import io.github.afalalabarce.mvvmproject.datasource.datastore.PreferencesDataStore
import io.github.afalalabarce.mvvmproject.datasource.remote.service.AppRemoteService
import io.github.afalalabarce.mvvmproject.model.interfaces.IKoinModuleLoader
import io.github.afalalabarce.mvvmproject.model.utilities.iif
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataSourceDependencyInjector: IKoinModuleLoader {
    override fun getKoinModules(context: Context): List<Module> = listOf(
        module {
            single { PreferencesDataStore(androidContext()) } // Preferences datastore MUST BE SINGLE!!!
            single{
                Room.databaseBuilder(context,
                    AppDatabase::class.java,
                    AppDatabase.DATABASE_NAME
                ).addMigrations(*AppDatabase.migrations.toTypedArray())
                .enableMultiInstanceInvalidation()
                .build()
            }
            single{ getKoin().get<AppDatabase>().dao() }
            single {
                Retrofit.Builder()
                    .baseUrl((BuildConfig.DEBUG).iif({ AppRemoteService.BASE_DEBUG_API_URL}, { AppRemoteService.BASE_API_URL } ))
                    .addConverterFactory(GsonConverterFactory.create(AppRemoteService.makeGson()))
                    .client(AppRemoteService.makeOkHttpClient(get()))
                    .build().create(AppRemoteService::class.java)
            }
            // TODO Add some factory, single, etc. Koin definitions
        },
    )
}
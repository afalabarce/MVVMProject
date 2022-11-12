package io.github.afalalabarce.mvvmproject.data.di

import android.content.Context
import io.github.afalalabarce.mvvmproject.data.datastore.PreferencesDatastoreRepository
import io.github.afalalabarce.mvvmproject.data.features.example.factory.ExampleEntityDataStoreFactory
import io.github.afalalabarce.mvvmproject.data.features.example.implementation.ExampleCacheDataStoreImpl
import io.github.afalalabarce.mvvmproject.data.features.example.implementation.ExampleRemoteDataStoreImpl
import io.github.afalalabarce.mvvmproject.data.features.example.interfaces.ExampleDataStore
import io.github.afalalabarce.mvvmproject.datasource.di.DataSourceDependencyInjector
import io.github.afalalabarce.mvvmproject.model.interfaces.IKoinModuleLoader
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

class DataDependencyInjector: IKoinModuleLoader {
    override fun getKoinModules(context: Context): List<Module> {
        return DataSourceDependencyInjector().getKoinModules(context)
            .union(listOf(
                module {
                    factory { PreferencesDatastoreRepository(get()) }

                    // TODO Add some factory, single, etc. Koin definitions
                    factory<ExampleDataStore>(named("cache")) { ExampleCacheDataStoreImpl(get()) }
                    factory<ExampleDataStore>(named("remote")) { ExampleRemoteDataStoreImpl(get(), get()) }
                    factory { ExampleEntityDataStoreFactory(get(named("cache")), get(named("remote"))) }
                },
            )
        ).toList()
    }
}
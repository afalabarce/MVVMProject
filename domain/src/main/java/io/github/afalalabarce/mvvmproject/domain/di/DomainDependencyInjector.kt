package io.github.afalalabarce.mvvmproject.domain.di

import android.content.Context
import io.github.afalalabarce.mvvmproject.data.di.DataDependencyInjector
import io.github.afalalabarce.mvvmproject.domain.usecase.example.ExampleEntityUseCase
import io.github.afalalabarce.mvvmproject.domain.usecase.settings.AppSettingsUseCase
import io.github.afalalabarce.mvvmproject.model.interfaces.IKoinModuleLoader
import org.koin.core.module.Module
import org.koin.dsl.module

class DomainDependencyInjector: IKoinModuleLoader {
    override fun getKoinModules(context: Context): List<Module> {
        return DataDependencyInjector().getKoinModules(context)
            .union(listOf(
                module {
                    factory { AppSettingsUseCase(get()) }

                    factory { ExampleEntityUseCase(get()) }
                    // TODO Add some factory, single, etc. Koin definitions
                },
            )
        ).toList()
    }
}
package io.github.afalalabarce.mvvmproject.di

import android.content.Context
import io.github.afalalabarce.mvvmproject.domain.di.DomainDependencyInjector
import io.github.afalalabarce.mvvmproject.model.interfaces.IKoinModuleLoader
import io.github.afalalabarce.mvvmproject.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class ApplicationDependencyInjector: IKoinModuleLoader {
    override fun getKoinModules(context: Context): List<Module> {
        return DomainDependencyInjector().getKoinModules(context)
            .union(listOf(
                module {
                       viewModel { MainViewModel(get(), get()) }
                    // TODO Add some factory, single, etc. Koin definitions
                },
            )
        ).toList()
    }
}
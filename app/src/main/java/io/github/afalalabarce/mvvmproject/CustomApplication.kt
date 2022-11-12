package io.github.afalalabarce.mvvmproject

import androidx.multidex.MultiDexApplication
import io.github.afalalabarce.mvvmproject.di.ApplicationDependencyInjector
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class CustomApplication: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@CustomApplication)
            modules(ApplicationDependencyInjector().getKoinModules(this@CustomApplication.applicationContext))
        }

    }
}
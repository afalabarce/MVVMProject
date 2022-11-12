package io.github.afalalabarce.mvvmproject.model.interfaces

import android.content.Context
import org.koin.core.module.Module

interface IKoinModuleLoader {
    fun getKoinModules(context: Context): List<Module>
}
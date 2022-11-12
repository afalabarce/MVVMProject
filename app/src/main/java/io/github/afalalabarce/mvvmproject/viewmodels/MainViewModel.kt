package io.github.afalalabarce.mvvmproject.viewmodels

import androidx.lifecycle.ViewModel
import io.github.afalalabarce.mvvmproject.domain.usecase.example.ExampleEntityUseCase
import io.github.afalalabarce.mvvmproject.domain.usecase.settings.AppSettingsUseCase

class MainViewModel(
    private val useCase: ExampleEntityUseCase,
    private val settingsUseCase: AppSettingsUseCase
): ViewModel() {
}
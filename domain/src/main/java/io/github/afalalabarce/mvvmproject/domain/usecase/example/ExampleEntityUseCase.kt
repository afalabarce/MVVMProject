package io.github.afalalabarce.mvvmproject.domain.usecase.example

import io.github.afalalabarce.mvvmproject.data.features.example.factory.ExampleEntityDataStoreFactory
import io.github.afalalabarce.mvvmproject.model.domain.ExampleDomainEntity
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@OptIn(FlowPreview::class)
class ExampleEntityUseCase(
    private val exampleDataFactory: ExampleEntityDataStoreFactory
) {
    fun getAllExampleEntities(): Flow<List<ExampleDomainEntity>> = this.exampleDataFactory.getRemoteDataStore().getAllExampleEntities()
    fun addOrUpdateEntities(vararg exampleDomainEntity: ExampleDomainEntity): Flow<List<ExampleDomainEntity>> = this.exampleDataFactory.getCacheDataStore().addOrUpdateExampleEntity(
        *exampleDomainEntity
    )
    fun syncEntities(): Flow<List<ExampleDomainEntity>> = this@ExampleEntityUseCase.exampleDataFactory.getCacheDataStore().getAllExampleEntities().flatMapConcat { entities ->
        this@ExampleEntityUseCase.exampleDataFactory.getRemoteDataStore().addOrUpdateExampleEntity(*entities.toTypedArray())
    }
}
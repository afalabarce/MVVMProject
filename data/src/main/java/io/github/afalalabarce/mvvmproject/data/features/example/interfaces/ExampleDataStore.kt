package io.github.afalalabarce.mvvmproject.data.features.example.interfaces

import io.github.afalalabarce.mvvmproject.model.domain.ExampleDomainEntity
import kotlinx.coroutines.flow.Flow

interface ExampleDataStore {
    fun getAllExampleEntities(): Flow<List<ExampleDomainEntity>>
    fun addOrUpdateExampleEntity(vararg exampleEntity: ExampleDomainEntity): Flow<List<ExampleDomainEntity>>
    fun deleteExampleEntity(vararg exampleEntity: ExampleDomainEntity): Flow<Int>
}
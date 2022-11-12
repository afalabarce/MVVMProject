package io.github.afalalabarce.mvvmproject.data.features.example.implementation

import io.github.afalalabarce.mvvmproject.data.features.example.interfaces.ExampleDataStore
import io.github.afalalabarce.mvvmproject.datasource.cache.db.AppDatabase
import io.github.afalalabarce.mvvmproject.datasource.cache.model.CacheExampleEntity
import io.github.afalalabarce.mvvmproject.model.domain.ExampleDomainEntity
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class ExampleCacheDataStoreImpl(
    private val appDatabase: AppDatabase
): ExampleDataStore {
    override fun getAllExampleEntities(): Flow<List<ExampleDomainEntity>> = this.appDatabase.dao().getAllExampleEntities().map { entities ->
        entities.map { e -> e.toDomain() }
    }

    override fun addOrUpdateExampleEntity(vararg exampleEntity: ExampleDomainEntity): Flow<List<ExampleDomainEntity>> = flow {
        val updatedOrInsertedIds = this@ExampleCacheDataStoreImpl.appDatabase.dao().addOrUpdateExampleEntity(
            *exampleEntity.map { e -> CacheExampleEntity.fromDomain(e) }.toTypedArray()
        )
        emitAll(
            this@ExampleCacheDataStoreImpl.appDatabase.dao().getExampleEntitiesByIds(updatedOrInsertedIds)
                    .map { x ->
                        x.map { y -> y.toDomain() }
                    }
        )
    }


    override fun deleteExampleEntity(vararg exampleEntity: ExampleDomainEntity): Flow<Int> = flow {
        val deletedIds = this@ExampleCacheDataStoreImpl.appDatabase.dao().deleteExampleEntity(
            exampleEntity = exampleEntity.map { e -> CacheExampleEntity.fromDomain(e) }.toTypedArray()
        )

        emit(deletedIds)
    }
}
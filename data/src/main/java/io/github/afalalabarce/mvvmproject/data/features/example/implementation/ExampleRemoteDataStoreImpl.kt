package io.github.afalalabarce.mvvmproject.data.features.example.implementation

import io.github.afalalabarce.mvvmproject.data.features.example.interfaces.ExampleDataStore
import io.github.afalalabarce.mvvmproject.datasource.cache.db.AppDatabase
import io.github.afalalabarce.mvvmproject.datasource.remote.service.AppRemoteService
import io.github.afalalabarce.mvvmproject.model.domain.ExampleDomainEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ExampleRemoteDataStoreImpl(
    private val remoteService: AppRemoteService,
    private val appDatabase: AppDatabase,
): ExampleDataStore {
    override fun getAllExampleEntities(): Flow<List<ExampleDomainEntity>> = flow {
        try{
            val resultRemote = this@ExampleRemoteDataStoreImpl.remoteService.getRemoteEntity()
            if (resultRemote.isSuccessful){
                val cacheExampleEntities = resultRemote.body()?.let { rData ->
                    rData.map { r -> r.toCacheEntity() }
                } ?: listOf()
                // in real life, we need a mechanism to control if we need to download or not some data from remote sources
                this@ExampleRemoteDataStoreImpl.appDatabase.dao().addOrUpdateExampleEntity(*cacheExampleEntities.toTypedArray())

                emitAll(
                    this@ExampleRemoteDataStoreImpl.appDatabase.dao().getAllExampleEntities().map { entities ->
                        entities.map { e -> e.toDomain() }
                    }
                )
            }
        }catch (_: Exception){
            emit(listOf())
        }
    }

    override fun addOrUpdateExampleEntity(vararg exampleEntity: ExampleDomainEntity): Flow<List<ExampleDomainEntity>> {
        throw NotImplementedError("${this.javaClass.simpleName} not implemented ${this.javaClass.enclosingMethod}")
    }

    override fun deleteExampleEntity(vararg exampleEntity: ExampleDomainEntity): Flow<Int> {
        throw NotImplementedError("${this.javaClass.simpleName} not implemented ${this.javaClass.enclosingMethod}")
    }
}
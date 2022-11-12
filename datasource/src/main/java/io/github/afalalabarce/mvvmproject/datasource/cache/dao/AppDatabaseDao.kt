package io.github.afalalabarce.mvvmproject.datasource.cache.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import io.github.afalalabarce.mvvmproject.datasource.cache.model.CacheExampleEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface AppDatabaseDao {
    @Transaction
    @Query("select * from example_entities")
    fun getAllExampleEntities(): Flow<List<CacheExampleEntity>>
    @Transaction
    @Query("select * from example_entities where entity_id in (:ids)")
    fun getExampleEntitiesByIds(ids: LongArray): Flow<List<CacheExampleEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun addOrUpdateExampleEntity(vararg exampleEntity: CacheExampleEntity): LongArray

    @Delete
    suspend fun deleteExampleEntity(vararg exampleEntity: CacheExampleEntity): Int
}
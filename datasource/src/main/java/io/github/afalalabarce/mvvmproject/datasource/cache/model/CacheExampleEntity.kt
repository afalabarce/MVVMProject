package io.github.afalalabarce.mvvmproject.datasource.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.afalalabarce.mvvmproject.datasource.mapper.ICacheEntity
import io.github.afalalabarce.mvvmproject.datasource.remote.model.RemoteExampleEntity
import io.github.afalalabarce.mvvmproject.model.domain.ExampleDomainEntity

@Entity(
    tableName = "example_entities"
)
data class CacheExampleEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name ="entity_id")
    val id: Long,
    @ColumnInfo(name = "entity_code")
    val code: String,
    @ColumnInfo(name = "entity_name")
    val name: String,
): ICacheEntity<RemoteExampleEntity, ExampleDomainEntity>() {
    override fun toRemote(): RemoteExampleEntity = RemoteExampleEntity(
        id = this.id,
        code = this.code,
        name = this.name,
        description = this.name,
    )

    override fun toDomain(): ExampleDomainEntity = ExampleDomainEntity(
        id = this.id,
        code = this.code,
        name = this.name,
    )

    companion object{
        fun fromDomain(domainEntity: ExampleDomainEntity): CacheExampleEntity = ICacheEntity.fromDomain(domainEntity){ d ->
            CacheExampleEntity(
                id = d.id,
                code = d.code,
                name = d.name,
            )
        }
    }
}

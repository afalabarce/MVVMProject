package io.github.afalalabarce.mvvmproject.datasource.remote.model

import com.google.gson.annotations.SerializedName
import io.github.afalalabarce.mvvmproject.datasource.cache.model.CacheExampleEntity
import io.github.afalalabarce.mvvmproject.datasource.mapper.ICacheEntity
import io.github.afalalabarce.mvvmproject.datasource.mapper.IRemoteEntity
import io.github.afalalabarce.mvvmproject.model.domain.ExampleDomainEntity
import io.github.afalalabarce.mvvmproject.model.exception.IllegalMappingException

data class RemoteExampleEntity(
    @SerializedName("remote_field_id") val id: Long,
    @SerializedName("remote_field_code") val code: String,
    @SerializedName("remote_field_name") val name: String,
    @SerializedName("remote_field_description") val description: String,
): IRemoteEntity<CacheExampleEntity, ExampleDomainEntity>() {
    override fun toCacheEntity(): CacheExampleEntity = CacheExampleEntity(
        id = this.id,
        code = this.code,
        name = this.name,
    )

    override fun toDomain(): ExampleDomainEntity {
        throw IllegalMappingException("${this.javaClass.simpleName} can't be mapped to Domain Entity")
    }

    companion object{
        fun fromDomain(domainEntity: ExampleDomainEntity): RemoteExampleEntity = ICacheEntity.fromDomain(domainEntity){ d ->
            throw IllegalMappingException("RemoteExampleEntity can't be mapped from Domain Entity")
        }
    }
}

package io.github.afalalabarce.mvvmproject.datasource.mapper

import io.github.afalalabarce.mvvmproject.model.interfaces.IDomain

abstract class IRemoteEntity<C: IMapperEntity, D: IDomain> : IMapperEntity {
    abstract fun toCacheEntity(): C
    abstract fun toDomain(): D

    companion object{
        fun <D: IDomain, R: IMapperEntity>fromDomain(domainEntity:D, domainEntityConversion: (D) -> R): R = domainEntityConversion(domainEntity)
    }
}
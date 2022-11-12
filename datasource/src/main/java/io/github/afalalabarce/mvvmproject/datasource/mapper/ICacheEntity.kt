package io.github.afalalabarce.mvvmproject.datasource.mapper

import io.github.afalalabarce.mvvmproject.model.interfaces.IDomain

abstract class ICacheEntity<R: IMapperEntity, D: IDomain>: IMapperEntity {
    abstract fun toRemote(): R
    abstract fun toDomain(): D

    companion object{
        fun <D: IDomain, C: IMapperEntity>fromDomain(domainEntity:D, domainEntityConversion: (D) -> C): C = domainEntityConversion(domainEntity)
    }
}
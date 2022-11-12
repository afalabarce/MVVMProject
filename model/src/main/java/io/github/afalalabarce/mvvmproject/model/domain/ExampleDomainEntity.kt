package io.github.afalalabarce.mvvmproject.model.domain

import io.github.afalalabarce.mvvmproject.model.interfaces.IDomain

data class ExampleDomainEntity(
    val id: Long,
    val code: String,
    val name: String,
): IDomain

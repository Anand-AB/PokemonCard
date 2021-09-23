package com.anand.pokemoncard.di

import com.anand.pokemoncard.data.contract.CommonApisRepo
import com.anand.pokemoncard.data.repository.CommonApisRepository
import org.koin.dsl.module.module

val repositoryModule = module {
    single<CommonApisRepo> { CommonApisRepository(get()) }
}
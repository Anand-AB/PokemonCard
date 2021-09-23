package com.anand.pokemoncard.di

import com.anand.pokemoncard.data.datasource.CommonApisDataSource
import com.anand.pokemoncard.data.ds.CommonApisRemoteDS
import org.koin.dsl.module.module

val dataSourceModule = module {
    single<CommonApisDataSource> { CommonApisRemoteDS(get()) }
}
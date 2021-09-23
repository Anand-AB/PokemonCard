package com.anand.pokemoncard.data.ds

import com.anand.pokemoncard.data.datasource.CommonApisDataSource
import com.anand.pokemoncard.data.models.CardsListRSP
import com.anand.pokemoncard.domain.network.CommonApiService
import kotlinx.coroutines.Deferred

class CommonApisRemoteDS constructor(private val commonApiService: CommonApiService) :
    CommonApisDataSource {

    override suspend fun getCardsListCallAsync(url: String): Deferred<CardsListRSP> {
        return commonApiService.getCardsListCallAsync(url)
    }
}
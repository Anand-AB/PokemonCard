package com.anand.pokemoncard.data.datasource

import com.anand.pokemoncard.data.models.CardsListRSP
import kotlinx.coroutines.Deferred

interface CommonApisDataSource {

    suspend fun getCardsListCallAsync(url: String): Deferred<CardsListRSP>

}
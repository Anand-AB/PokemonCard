package com.anand.pokemoncard.domain.network

import com.anand.pokemoncard.data.models.CardsListRSP
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Url

interface CommonApiService {

    @GET
    fun getCardsListCallAsync(
        @Url url: String
    ): Deferred<CardsListRSP>

}
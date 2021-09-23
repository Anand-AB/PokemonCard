package com.anand.pokemoncard.data.repository

import com.anand.pokemoncard.data.BaseRepository
import com.anand.pokemoncard.data.Either
import com.anand.pokemoncard.data.contract.CommonApisRepo
import com.anand.pokemoncard.data.datasource.CommonApisDataSource
import com.anand.pokemoncard.data.models.CardsListRSP
import com.anand.pokemoncard.domain.exceptions.MyException

class CommonApisRepository constructor(
    private val commonApisDataSource: CommonApisDataSource
) : CommonApisRepo,
    BaseRepository() {

    override suspend fun getCardsListCall(url: String): Either<MyException, CardsListRSP> {
        return either(commonApisDataSource.getCardsListCallAsync(url))
    }

}
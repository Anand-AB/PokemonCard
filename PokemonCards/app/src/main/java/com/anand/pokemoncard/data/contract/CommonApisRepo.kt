package com.anand.pokemoncard.data.contract

import com.anand.pokemoncard.data.Either
import com.anand.pokemoncard.data.models.CardsListRSP
import com.anand.pokemoncard.domain.exceptions.MyException

interface CommonApisRepo {

    suspend fun getCardsListCall(url: String): Either<MyException, CardsListRSP>

}
package com.anand.pokemoncard.data

import com.anand.pokemoncard.domain.exceptions.MyException
import com.anand.pokemoncard.presentation.utility.AppConstants.Companion.INTERNET_ERROR
import com.anand.pokemoncard.presentation.utility.AppConstants.Companion.TEXT_SOMETHING_WENT_WRONG
import com.anand.pokemoncard.presentation.utility.AppConstants.Companion.TEXT_TUNABLE_TO_PARSE_API_RESP
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException
import kotlinx.coroutines.Deferred
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseRepository {

    suspend fun <R> either(service: Deferred<R>): Either<MyException, R> {
        return try {
            Either.Right(service.await())
        } catch (e: Exception) {
            if (e is HttpException) {
                if (e.code() == 422) {
                    Either.Left(transformException(e))
                } else {
                    e.printStackTrace()
                    Either.Left(transformException(e))
                }
            } else {
                e.printStackTrace()
                Either.Left(transformException(e))
            }
        }
    }

    private fun transformException(e: Exception): MyException {
        if (e is HttpException) {
            when (e.code()) {
                422 -> return MyException.JsonException(TEXT_TUNABLE_TO_PARSE_API_RESP, e)
                502 -> return MyException.JsonException(TEXT_TUNABLE_TO_PARSE_API_RESP, e)
                500 -> return MyException.JsonException(
                    TEXT_SOMETHING_WENT_WRONG,
                    e
                )
            }
        } else {
            if (e is ConnectException || e is UnknownHostException || e is SocketTimeoutException || e is SocketException) {
                return MyException.InternetConnectionException(INTERNET_ERROR, e)
            } else if (e is IllegalStateException || e is JsonSyntaxException || e is MalformedJsonException) {
                return MyException.JsonException(TEXT_TUNABLE_TO_PARSE_API_RESP, e)
            }
        }
        return MyException.UnknownException(e)
    }

}
package com.anand.pokemoncard

import android.app.Application
import android.content.ContextWrapper
import com.anand.pokemoncard.data.models.CardListData
import com.anand.pokemoncard.di.*
import com.anand.pokemoncard.domain.network.CommonApiService
import com.anand.pokemoncard.presentation.utility.AppConstants
import com.anand.pokemoncard.presentation.utility.AppConstants.Companion.BASE_URL
import com.anand.pokemoncard.presentation.utility.Helper
import com.anand.pokemoncard.presentation.utility.PrefKeys
import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.android.startKoin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        mAppInstance = this
        authKey = Helper.getStringValue(AppConstants.PREF_KEY_AUTH_KEY)
        init()
    }

    private fun init() {
        startKoin(
            this, arrayListOf(
                networkModule,
                repositoryModule,
                dataSourceModule,
                viewModelModule
            )
        )

        Prefs.Builder()
            .setContext(this@MyApplication)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()

        val httpLoggingInterceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        // We need to prepare a custom OkHttp client because need to use our custom call interceptor.
        // to be able to authenticate our requests.
        val builder = OkHttpClient.Builder()
            .connectTimeout(90L, TimeUnit.SECONDS)
            .readTimeout(90L, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .cookieJar(MyCookieJar())
        // We add the interceptor to OkHttpClient.
        // It will add authentication headers to every call we make.
//        builder.interceptors().add(AuthenticationInterceptor())
        val client = builder.build()

        apiErrorHandler = Retrofit.Builder() // Create retrofit builder.
            .baseUrl(BASE_URL) // Base url for the api has to end with a slash.
            .addConverterFactory(GsonConverterFactory.create()) // Use GSON converter for JSON to POJO object mapping.
            .client(client) // Here we set the custom OkHttp client we just created.
            .build()
            .create(CommonApiService::class.java) // We create an API using the interface we defined.

    }

    companion object {
        var apiErrorHandler: CommonApiService? = null
        private var mAppInstance: MyApplication? = null
        var authKey: String? = null
        private var cardsListData: CardListData? = null

        fun getAppInstance(): MyApplication? {
            return mAppInstance
        }

        fun setSelectedCards(cardsListData: CardListData?) {
            if (cardsListData != null) {
                this.cardsListData = cardsListData
                this.cardsListData?.apply {
                    Prefs.putString(PrefKeys.SelectedCardsData, Gson().toJson(this))
                }
            }
        }


    }
}
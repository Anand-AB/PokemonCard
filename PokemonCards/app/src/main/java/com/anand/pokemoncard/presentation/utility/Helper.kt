package com.anand.pokemoncard.presentation.utility

import android.content.Context
import android.content.SharedPreferences
import com.anand.pokemoncard.MyApplication


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class Helper {
    companion object {
        private var sharedpreferences: SharedPreferences? = null
        private val PREFERENCE = "pref_health_provider"

        /**
         * Get string value from shared preferences
         *
         * @param key key of value
         * @return value of the key
         */
        fun getStringValue(key: String): String? {
            sharedpreferences = MyApplication.getAppInstance()
                ?.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            return sharedpreferences?.getString(key, "")
        }
    }
}
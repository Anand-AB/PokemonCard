package com.anand.pokemoncard.presentation.utility

interface AppConstants {
    companion object {

        ////////////////////////////////////////// Local Store Constants /////////////////////////////////////////////////
        const val PREF_KEY_AUTH_KEY = "pref_auth_key"

        ////////////////////////////////////////// API Constants /////////////////////////////////////////////////
        const val BASE_URL = "https://api.pokemontcg.io/v2/"
        const val GET_CARD = BASE_URL + "cards?pageSize=20"

        ////////////////////////////////////////// Error values //////////////////////////////////////////
        const val TEXT_TUNABLE_TO_PARSE_API_RESP = "Unable to parse api response"
        const val TEXT_SOMETHING_WENT_WRONG = "Something went wrong"
        const val INTERNET_ERROR = "Internet connection not available"
        const val TEXT_NO_DATA = "No Data Available"

        ////////////////////////////////////////// Card List values //////////////////////////////////////////
        const val TEXT_RELEVANCE = "Relevance"
        const val TEXT_LEVEL_HIGH_TO_LOW = "Level - High to Low"
        const val TEXT_LEVEL_LOW_TO_HIGH = "Level - Low to High"
        const val TEXT_HP_LEVEL_HIGH_TO_LOW = "Hp - High to Low"
        const val TEXT_HP_LOW_TO_HIGH = "Hp - Low to High"

        ////////////////////////////////////////// Card Detail values //////////////////////////////////////////
        const val TEXT_NAME = "Name : "
        const val TEXT_TEXT = "Text : "
        const val TEXT_TYPE = "Type : "
        const val TEXT_VALUE = "Value : "
        const val TEXT_CONVERTED_ENERGY_COST = "Converted Energy Cost : "
        const val TEXT_DAMAGE = "Damage : "
        const val TEXT_COST = "Cost : "

        ////////////////////////////////////////// Extension values //////////////////////////////////////////
        const val TEXT_APP = "App"
        const val TEXT_OK = "OK"

        ////////////////////////////////////////// commonly using values //////////////////////////////////////////
        const val BLANK_STRING = ""
        const val COMMA_SPACE_STRING = ", "

        const val CHIVO_REGULAR_TTF = "chivo_regular.ttf"
        const val PUBLIC_SANS_TTF = "publicsans_regular.ttf"
        const val QUEENS_PARK_TTF = "queens_park_bold.ttf"
        const val QUEENS_PARK_NORMAL_TTF = "queens_park.ttf"
    }
}
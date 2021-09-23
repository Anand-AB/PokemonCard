package com.anand.pokemoncard.data.models

/**
 * Created by Anand A <anandabktda@gmail.com>
 * The model file contains Cards list params
 * */

class CardsListRSP {
    var data: List<CardListData>? = emptyList()
}

data class CardListData(
    val name: String,
    val level: String?,
    val hp: String?,
    val types: List<String>? = emptyList(),
    val subtypes: List<String>? = emptyList(),
    val attacks: List<AttacksData>? = emptyList(),
    val weaknesses: List<WeaknessesData>? = emptyList(),
    val abilities: List<AbilitiesData>? = emptyList(),
    val resistances: List<ResistancesData>? = emptyList(),
    val set: SetData? = null
)

data class CardDetailData(
    val name: String,
    val level: String,
    val hp: String,
    val logo: String,
    val types: String,
    val subtypes: String,
    val attacks: String,
    val weaknesses: String,
    val abilities: String,
    val resistances: String
)

data class SetData(
    val images: ImageData? = null
)

data class ImageData(
    val symbol: String? = null,
    val logo: String? = null
)

data class AttacksData(
    val name: String? = null,
    val damage: String? = null,
    val text: String? = null,
    val convertedEnergyCost: Int? = null,
    val cost: List<String>? = emptyList()
)

data class WeaknessesData(
    val type: String? = null,
    val value: String? = null
)

data class AbilitiesData(
    val name: String? = null,
    val text: String? = null,
    val type: String? = null
)

data class ResistancesData(
    val value: String? = null,
    val type: String? = null
)
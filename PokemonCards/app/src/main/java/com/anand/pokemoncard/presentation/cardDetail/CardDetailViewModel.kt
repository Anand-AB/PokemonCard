package com.anand.pokemoncard.presentation.cardDetail

import androidx.lifecycle.MutableLiveData
import com.anand.pokemoncard.data.models.*
import com.anand.pokemoncard.presentation.core.BaseViewModel
import com.anand.pokemoncard.presentation.utility.AppConstants
import com.anand.pokemoncard.presentation.utility.AppConstants.Companion.BLANK_STRING
import com.anand.pokemoncard.presentation.utility.PrefKeys
import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs

/**
 * Created by Anand A <anandabktda@gmail.com>
 * The ViewModel used for process cards detail data
 * */

class CardDetailViewModel : BaseViewModel() {

    val cardsDetailLiveData: MutableLiveData<CardDetailData> = MutableLiveData()

    fun getCardDetail() {

        val cardsDetailStr = Prefs.getString(PrefKeys.SelectedCardsData, null)
        val cardsData: CardListData

        cardsDetailStr?.let {
            cardsData = Gson().fromJson(it, CardListData::class.java)

            val logo = cardsData.set?.images?.logo
            val types = getTypes(cardsData.types)
            val subTypes = getSubTypes(cardsData.subtypes)
            val attacks = getAttacks(cardsData.attacks)
            val weaknesses = getWeaknesses(cardsData.weaknesses)
            val abilities = getAbilities(cardsData.abilities)
            val resistances = getResistances(cardsData.resistances)

            val cardDetailData = CardDetailData(
                getNotNullData(cardsData.name),
                getNotNullData(cardsData.level),
                getNotNullData(cardsData.hp),
                getNotNullData(logo),
                types,
                subTypes,
                attacks,
                weaknesses,
                abilities,
                resistances
            )
            cardsDetailLiveData.value = cardDetailData
        }

    }

    // get type string
    private fun getTypes(typeList: List<String>?): String {
        var types = BLANK_STRING

        typeList?.let {
            if (typeList.isNotEmpty()) {
                if (typeList.size > 1) {
                    for ((typeIndex, type) in typeList.withIndex()) {
                        types += type
                        if (typeIndex != typeList.lastIndex) {
                            types += AppConstants.COMMA_SPACE_STRING
                        }
                    }
                } else {
                    types = typeList[0]
                }
            }
        }

        return types
    }

    // get subtype string
    private fun getSubTypes(subTypeList: List<String>?): String {
        var subTypes = BLANK_STRING

        subTypeList?.let {
            if (subTypeList.isNotEmpty()) {
                if (subTypeList.size > 1) {
                    for ((subTypeIndex, subType) in subTypeList.withIndex()) {
                        subTypes += subType
                        if (subTypeIndex != subTypeList.lastIndex) {
                            subTypes += AppConstants.COMMA_SPACE_STRING
                        }
                    }
                } else {
                    subTypes = subTypeList[0]
                }
            }
        }

        return subTypes
    }

    // get attack string
    private fun getAttacks(attackList: List<AttacksData>?): String {
        var attacks = BLANK_STRING

        attackList?.let {
            if (attackList.isNotEmpty()) {

                if (attackList.size > 1) {
                    for ((attackIndex, attack) in attackList.withIndex()) {
                        attacks =
                            generateAttackString(attacks, attack, attackIndex, attackList.lastIndex)
                    }
                } else {
                    attacks = generateAttackString(attacks, attackList[0], 0, 0)
                }
            }
        }

        return attacks
    }

    // generate attack string data from AttacksData
    private fun generateAttackString(
        currentAttacks: String,
        attack: AttacksData,
        attackIndex: Int,
        lastIndex: Int
    ): String {
        var attacks = currentAttacks
        val name = AppConstants.TEXT_NAME + attack.name + "\n"
        val convertedEnergyCost =
            AppConstants.TEXT_CONVERTED_ENERGY_COST + attack.convertedEnergyCost + "\n"

        var damage = BLANK_STRING
        if (attack.damage!!.isNotEmpty())
            damage =
                AppConstants.TEXT_DAMAGE + attack.damage + "\n"

        val text =
            AppConstants.TEXT_TEXT + attack.text + "\n"

        val cost =
            getAttackCost(attack.cost, attackIndex, lastIndex)

        attacks = if (attackIndex != lastIndex)
            attacks + name + convertedEnergyCost + damage + text + cost + "\n"
        else
            attacks + name + convertedEnergyCost + damage + text + cost

        return attacks
    }

    // get weakness string
    private fun getWeaknesses(weaknessesList: List<WeaknessesData>?): String {
        var weaknesses = BLANK_STRING

        weaknessesList?.let {
            if (weaknessesList.isNotEmpty()) {

                if (weaknessesList.size > 1) {
                    for ((weaknessIndex, weakness) in weaknessesList.withIndex()) {

                        weaknesses = generateWeaknessString(
                            weaknesses,
                            weakness,
                            weaknessIndex,
                            weaknessesList.lastIndex
                        )
                    }
                } else {
                    weaknesses = generateWeaknessString(weaknesses, weaknessesList[0], 0, 0)
                }
            }
        }

        return weaknesses
    }

    // generate weakness string data from WeaknessesData
    private fun generateWeaknessString(
        currentWeakness: String,
        weakness: WeaknessesData,
        weaknessIndex: Int,
        lastIndex: Int
    ): String {

        var weaknesses = currentWeakness
        val type = AppConstants.TEXT_TYPE + weakness.type + "\n"

        var value =
            AppConstants.TEXT_VALUE + weakness.value

        if (weaknessIndex != lastIndex)
            value += "\n"

        weaknesses = if (weaknessIndex != lastIndex)
            weaknesses + type + value + "\n"
        else
            weaknesses + type + value

        return weaknesses
    }

    // get abilities string
    private fun getAbilities(abilitiesList: List<AbilitiesData>?): String {
        var abilities = BLANK_STRING

        abilitiesList?.let {
            if (abilitiesList.isNotEmpty()) {

                if (abilitiesList.size > 1) {
                    for ((abilityIndex, ability) in abilitiesList.withIndex()) {
                        abilities =
                            generateAbilityString(
                                abilities,
                                ability,
                                abilityIndex,
                                abilitiesList.lastIndex
                            )
                    }
                } else {
                    abilities =
                        generateAbilityString(abilities, abilitiesList[0], 0, 0)
                }
            }
        }

        return abilities
    }

    // generate ability string data from WeaknessesData
    private fun generateAbilityString(
        currentAbility: String,
        ability: AbilitiesData,
        abilityIndex: Int,
        lastIndex: Int
    ): String {

        var abilities = currentAbility
        val name = AppConstants.TEXT_NAME + ability.name + "\n"
        val text = AppConstants.TEXT_TEXT + ability.text + "\n"
        var type = AppConstants.TEXT_TYPE + ability.type

        if (abilityIndex != lastIndex)
            type += "\n"

        abilities = if (abilityIndex != lastIndex)
            abilities + name + text + type + "\n"
        else
            abilities + name + text + type

        return abilities
    }

    // get resistances string
    private fun getResistances(resistancesList: List<ResistancesData>?): String {
        var resistances = BLANK_STRING

        resistancesList?.let {
            if (it.isNotEmpty()) {

                if (it.size > 1) {
                    for ((resistanceIndex, resistance) in it.withIndex()) {

                        resistances =
                            generateResistanceString(
                                resistances,
                                resistance,
                                resistanceIndex,
                                it.lastIndex
                            )
                    }
                } else {

                    resistances =
                        generateResistanceString(resistances, it[0], 0, 0)
                }
            }
        }

        return resistances
    }

    // generate ability string data from WeaknessesData
    private fun generateResistanceString(
        currentResistance: String,
        resistance: ResistancesData,
        resistanceIndex: Int,
        lastIndex: Int
    ): String {
        var resistances = currentResistance
        val value = AppConstants.TEXT_NAME + resistance.value + "\n"
        val type = AppConstants.TEXT_TYPE + resistance.type + "\n"

        resistances = if (resistanceIndex != lastIndex)
            resistances + value + type + "\n"
        else
            resistances + value + type

        return resistances
    }

    //attack cost string generate
    private fun getAttackCost(costList: List<String>?, attackIndex: Int, lastIndex: Int): String {
        var cost = BLANK_STRING

        costList?.let {
            if (costList.isNotEmpty()) {
                cost = AppConstants.TEXT_COST

                for (costIndex in costList.indices)
                    when {
                        costIndex != costList.lastIndex -> cost =
                            cost + costList[costIndex] + AppConstants.COMMA_SPACE_STRING
                        attackIndex != lastIndex -> cost += costList[costIndex] + "\n"
                        else -> cost += costList[costIndex]
                    }
            }
        }
        return cost
    }

    private fun getNotNullData(data: String?): String {
        var finalData: String = BLANK_STRING
        data?.let {
            finalData = it
        }
        return finalData
    }

}
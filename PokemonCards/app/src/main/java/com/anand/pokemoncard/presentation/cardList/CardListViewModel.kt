package com.anand.pokemoncard.presentation.cardList

import androidx.lifecycle.MutableLiveData
import com.anand.pokemoncard.data.contract.CommonApisRepo
import com.anand.pokemoncard.data.models.CardListData
import com.anand.pokemoncard.data.models.CardsListRSP
import com.anand.pokemoncard.presentation.core.BaseViewModel
import com.anand.pokemoncard.presentation.utility.AppConstants
import com.anand.pokemoncard.presentation.utility.AppConstants.Companion.GET_CARD
import com.anand.pokemoncard.presentation.utility.AppConstants.Companion.INTERNET_ERROR
import com.anand.pokemoncard.presentation.utility.AppConstants.Companion.TEXT_HP_LEVEL_HIGH_TO_LOW
import com.anand.pokemoncard.presentation.utility.AppConstants.Companion.TEXT_HP_LOW_TO_HIGH
import com.anand.pokemoncard.presentation.utility.AppConstants.Companion.TEXT_LEVEL_HIGH_TO_LOW
import com.anand.pokemoncard.presentation.utility.AppConstants.Companion.TEXT_LEVEL_LOW_TO_HIGH
import com.anand.pokemoncard.presentation.utility.AppConstants.Companion.TEXT_NO_DATA
import com.anand.pokemoncard.presentation.utility.AppConstants.Companion.TEXT_RELEVANCE
import kotlinx.coroutines.launch

/**
 * Created by Anand A <anandabktda@gmail.com>
 * The ViewModel used for process cards list data
 * */

class CardListViewModel constructor(private val commonApisRepo: CommonApisRepo) :
    BaseViewModel() {

    val cardsListLiveData: MutableLiveData<CardsListRSP> = MutableLiveData()
    val cardsListFilteredLiveData: MutableLiveData<List<CardListData>> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()

    fun getCardsList(isNetworkAvailable: Boolean) {

        if (isNetworkAvailable) {
            launch {
                postValue(
                    commonApisRepo.getCardsListCall(GET_CARD), cardsListLiveData
                )
            }
        } else {
            errorLiveData.value = INTERNET_ERROR
        }
    }

    // filter action
    fun getFilteredList(selectedFilter: String) {

        var cardsFiltered: List<CardListData> = arrayListOf()

        when (selectedFilter) {

            // if filtered option is Relevance
            TEXT_RELEVANCE -> cardsListLiveData.value?.data?.let {
                cardsFiltered = it
            }

            // if filtered option is Level - High to Low
            TEXT_LEVEL_HIGH_TO_LOW -> cardsFiltered = getHighToLowLevelList()

            // if filtered option is Level - Low to High
            TEXT_LEVEL_LOW_TO_HIGH -> cardsFiltered = getHighToLowLevelList().reversed()

            // if filtered option is Hp - High to Low
            TEXT_HP_LEVEL_HIGH_TO_LOW -> cardsFiltered = getHighToLowHpList()

            // if filtered option is Hp - Low to High
            TEXT_HP_LOW_TO_HIGH -> cardsFiltered = getHighToLowHpList().reversed()
        }

        if (cardsFiltered.isNotEmpty()) {
            cardsListFilteredLiveData.value = cardsFiltered
        } else {
            errorLiveData.value = TEXT_NO_DATA
        }

    }

    // return filtered list - hp high to low
    private fun getHighToLowHpList(): ArrayList<CardListData> {
        val hpList = arrayListOf<CardListData>()

        cardsListLiveData.value?.data?.let { cardList ->
            for (card in cardList) {
                if (hpList.isNotEmpty()) {
                    for ((hpIndex, hpCard) in hpList.withIndex()) {

                        if (card.hp == null) {
                            hpList.add(card)
                            break
                        } else if (hpCard.hp == null) {
                            hpList.add(hpIndex, card)
                            break
                        } else if (hpCard.hp.toInt() < card.hp.toInt()) {

                            hpList.add(hpIndex, card)
                            break
                        } else if (hpIndex == hpList.lastIndex) {
                            hpList.add(card)
                            break
                        }
                    }
                } else
                    hpList.add(card)
            }
        }

        return hpList
    }

    // return filtered list - level high to low
    private fun getHighToLowLevelList(): ArrayList<CardListData> {
        val levelList = arrayListOf<CardListData>()

        cardsListLiveData.value?.data?.let { cardList ->
            for (card in cardList) {
                if (levelList.isNotEmpty()) {
                    for ((levelIndex, levelCard) in levelList.withIndex()) {

                        if (card.level == null) {
                            levelList.add(card)
                            break
                        } else if (levelCard.level == null) {
                            levelList.add(levelIndex, card)
                            break
                        } else if (levelCard.level.toInt() < card.level.toInt()) {
                            levelList.add(levelIndex, card)
                            break
                        } else if (levelIndex == levelList.lastIndex) {
                            levelList.add(card)
                            break
                        }
                    }
                } else
                    levelList.add(card)
            }
        }
        return levelList
    }

    // search query action
    fun getSearchedList(query: String) {
        if (query != AppConstants.BLANK_STRING) {
            val cardsSearch = arrayListOf<CardListData>()

            cardsListLiveData.value?.data?.let { cardList ->
                for (card in cardList) {
                    if (card.name.contains(query, true)) {
                        cardsSearch.add(card)
                    }
                }
            }

            if (cardsSearch.isNotEmpty()) {
                cardsListFilteredLiveData.value = cardsSearch
            } else {
                errorLiveData.value = TEXT_NO_DATA
            }

        } else {
            cardsListLiveData.value?.data?.let { cardList ->
                if (cardList.isNotEmpty()) {
                    cardsListFilteredLiveData.value = cardList
                } else {
                    errorLiveData.value = TEXT_NO_DATA
                }
            }
        }
    }
}
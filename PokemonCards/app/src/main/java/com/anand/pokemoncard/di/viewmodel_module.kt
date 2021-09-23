package com.anand.pokemoncard.di

import com.anand.pokemoncard.presentation.cardDetail.CardDetailViewModel
import com.anand.pokemoncard.presentation.cardList.CardListViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { CardListViewModel(get()) }
    viewModel { CardDetailViewModel() }
}

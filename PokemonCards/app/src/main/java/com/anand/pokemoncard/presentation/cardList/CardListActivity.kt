package com.anand.pokemoncard.presentation.cardList

import android.view.View
import android.widget.RadioButton
import android.widget.SearchView
import androidx.lifecycle.Observer
import com.anand.pokemoncard.MyApplication
import com.anand.pokemoncard.R
import com.anand.pokemoncard.data.models.CardListData
import com.anand.pokemoncard.presentation.adapter.CardsListAdapter
import com.anand.pokemoncard.presentation.core.BaseActivity
import com.anand.pokemoncard.presentation.utility.UiHelper
import com.anand.pokemoncard.presentation.utility.isNetworkConnected
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_card_list.*
import kotlinx.android.synthetic.main.filter_bottom_sheet.view.*
import kotlinx.android.synthetic.main.layout_toolbar_main.*
import kotlinx.android.synthetic.main.layout_toolbar_main.view.*
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * Created by Anand A <anandabktda@gmail.com>
 * The Activity used for showing card list
 * */

class CardListActivity : BaseActivity() {

    private var cardsListAdapter: CardsListAdapter? = null
    private var selectedFilterId: Int? = null

    override fun getLayout(): Int {
        return R.layout.activity_card_list
    }

    private val cardListViewModel: CardListViewModel by viewModel()
    override fun getBaseViewModel() = cardListViewModel

    override fun initiation() {
        super.initiation()

        setSupportActionBar(toolbar)
        setAdapter()

        cardListViewModel.getCardsList(isNetworkConnected())
        showProgress()
    }

    private fun setAdapter() {

        cardsListAdapter = object : CardsListAdapter(this) {

            override fun itemOnCLick(data: CardListData) {
                super.itemOnCLick(data)

                MyApplication.setSelectedCards(data)
                UiHelper.startCardsDetail(this@CardListActivity)
            }
        }

        rv_cards.adapter = cardsListAdapter

    }

    // update card list with data
    private fun setCardsData(cardsList: List<CardListData>) {
        cardsListAdapter?.clear()
        cardsListAdapter?.addAll(cardsList)

        rv_cards.visibility = View.VISIBLE
        tv_no_data.visibility = View.GONE
    }

    // update card list empty case
    private fun cardsListEmpty(message: String) {
        rv_cards.visibility = View.GONE
        tv_no_data.visibility = View.VISIBLE
        tv_no_data.text = message
    }

    override fun setOnClickListener() {
        super.setOnClickListener()

        search!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(query: String): Boolean {
                cardListViewModel.getSearchedList(query)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                cardListViewModel.getSearchedList(query)
                return false
            }

        })

        iv_filter.setOnClickListener {
            search.clearFocus()
            val view = layoutInflater.inflate(R.layout.filter_bottom_sheet, null)
            val dialog = BottomSheetDialog(this@CardListActivity)
            dialog.setContentView(view)
            dialog.show()

            selectedFilterId?.let {
                val rb = view.findViewById(selectedFilterId!!) as RadioButton
                rb.isChecked = true
            } ?: kotlin.run {
                val rb = view.findViewById(R.id.rb_relevance) as RadioButton
                rb.isChecked = true
            }

            view.rg_filter.setOnCheckedChangeListener { _, checkedId ->
                val rb = view.findViewById(checkedId) as RadioButton
                selectedFilterId = checkedId
                dialog.dismiss()
                cardListViewModel.getFilteredList(rb.text.toString())
            }
        }
    }

    override fun getApiResponse() {
        super.getApiResponse()

        cardListViewModel.cardsListLiveData.observe(this, Observer {
            hideProgress()

            it.data?.isNotEmpty()?.let { _ ->
                setCardsData(it.data!!)
            } ?: kotlin.run {
                cardsListEmpty(getString(R.string.text_no_data))
            }

        })

        cardListViewModel.cardsListFilteredLiveData.observe(this, Observer {
            hideProgress()
            setCardsData(it)
        })

        cardListViewModel.errorLiveData.observe(this, Observer {
            hideProgress()
            cardsListEmpty(it)
        })

    }

}

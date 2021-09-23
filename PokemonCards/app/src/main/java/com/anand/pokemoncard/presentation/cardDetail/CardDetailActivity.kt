package com.anand.pokemoncard.presentation.cardDetail

import android.graphics.PorterDuff
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import com.anand.pokemoncard.R
import com.anand.pokemoncard.data.models.CardDetailData
import com.anand.pokemoncard.presentation.core.BaseActivity
import com.anand.pokemoncard.presentation.utility.loadImage
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import kotlinx.android.synthetic.main.activity_card_detail.*
import kotlinx.android.synthetic.main.content_scrolling.*
import kotlinx.android.synthetic.main.layout_toolbar_detail.*
import kotlinx.android.synthetic.main.layout_toolbar_detail.view.*
import kotlinx.android.synthetic.main.layout_toolbar_main.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by Anand A <anandabktda@gmail.com>
 * The Activity used for showing card details
 * */

class CardDetailActivity : BaseActivity() {

    override fun getLayout(): Int {
        return R.layout.activity_card_detail
    }

    private val cardDetailViewModel: CardDetailViewModel by viewModel()
    override fun getBaseViewModel() = cardDetailViewModel

    override fun initiation() {
        super.initiation()

        setSupportActionBar(toolbar)
        cardDetailViewModel.getCardDetail()
    }

    private fun setData(cardDetailData: CardDetailData) {
        tv_name.text = cardDetailData.name
        tv_level.text = cardDetailData.level
        tv_hp.text = cardDetailData.hp
        header.loadImage(cardDetailData.logo)
        tv_types.text = cardDetailData.types

        if (cardDetailData.subtypes.isNotEmpty()) {
            tv_sub_types.text = cardDetailData.subtypes
            ll_sub_type.visibility = View.VISIBLE
        }

        if (cardDetailData.attacks.isNotEmpty()) {
            tv_attacks.text = cardDetailData.attacks
            ll_attacks.visibility = View.VISIBLE
        }

        if (cardDetailData.weaknesses.isNotEmpty()) {
            tv_weakness.text = cardDetailData.weaknesses
            ll_weakness.visibility = View.VISIBLE
        }

        if (cardDetailData.abilities.isNotEmpty()) {
            tv_abilities.text = cardDetailData.abilities
            ll_abilities.visibility = View.VISIBLE
        }

        if (cardDetailData.resistances.isNotEmpty()) {
            tv_resistances.text = cardDetailData.resistances
            ll_resistances.visibility = View.VISIBLE
        }
    }

    override fun setOnClickListener() {
        super.setOnClickListener()

        app_bar.addOnOffsetChangedListener(OnOffsetChangedListener { _, verticalOffset ->
            if (toolbar_layout.height + verticalOffset < 2 * ViewCompat.getMinimumHeight(
                    toolbar_layout
                )
            ) {

                val param = nsv_main.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(0, 0, 0, 0)
                nsv_main.layoutParams =
                    param

                toolbar_detail.iv_back!!.setColorFilter(
                    ContextCompat.getColor(this@CardDetailActivity, R.color.saved_blue),
                    PorterDuff.Mode.SRC_ATOP
                )

            } else {

                val param = nsv_main.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(0, -45, 0, 0)
                nsv_main.layoutParams =
                    param

                toolbar_detail.iv_back!!.setColorFilter(
                    ContextCompat.getColor(this@CardDetailActivity, R.color.title_color),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
        })

        toolbar_detail.iv_back.setOnClickListener {
            onBackPressed()
        }

    }

    override fun getApiResponse() {
        super.getApiResponse()

        cardDetailViewModel.cardsDetailLiveData.observe(this, Observer {
            setData(it)
        })
    }

}

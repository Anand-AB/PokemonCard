package com.anand.pokemoncard.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.anand.pokemoncard.R
import com.anand.pokemoncard.data.models.CardListData
import com.anand.pokemoncard.presentation.utility.AppConstants.Companion.BLANK_STRING
import com.anand.pokemoncard.presentation.utility.AppConstants.Companion.COMMA_SPACE_STRING
import com.anand.pokemoncard.presentation.utility.loadImageRoundCorner
import kotlinx.android.synthetic.main.card_row_layout.view.*

/**
 * Created by Anand A <anandabktda@gmail.com>
 * Cards list Adapter
 * */

open class CardsListAdapter
    (val context: Context?) : RecyclerView.Adapter<CardsListAdapter.ViewHolder>() {

    var cardsList: MutableList<CardListData> = arrayListOf()

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view =
            LayoutInflater.from(p0.context).inflate(R.layout.card_row_layout, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cardsList.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.bind(cardsList[p1])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                itemOnCLick(cardsList[adapterPosition])
            }
        }

        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(data: CardListData) {

            try {

                itemView.tv_name.text = data.name
                itemView.tv_level.text = data.level
                itemView.tv_hp.text = data.hp

                if (data.types!!.isNotEmpty()) {
                    var types = BLANK_STRING
                    if (data.types.size > 1)
                        for (i in data.types.indices) {
                            types += data.types[i]
                            if (i != data.types.lastIndex)
                                types += COMMA_SPACE_STRING
                        }
                    else
                        types = data.types[0]

                    itemView.tv_types.text = types
                }

                itemView.iv_card.loadImageRoundCorner(data.set!!.images!!.logo!!)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    open fun itemOnCLick(data: CardListData) {}

    fun addAll(cardsList: List<CardListData>) {
        this.cardsList.addAll(cardsList)
        notifyDataSetChanged()
    }

    fun clear() {
        this.cardsList.clear()
    }
}
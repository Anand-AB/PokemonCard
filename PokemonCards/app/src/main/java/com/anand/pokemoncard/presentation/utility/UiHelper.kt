package com.anand.pokemoncard.presentation.utility

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.anand.pokemoncard.R
import com.anand.pokemoncard.presentation.cardDetail.CardDetailActivity

class UiHelper {

    companion object {

        fun startCardsDetail(
            context: AppCompatActivity,
            clearTop: Boolean? = false
        ) {
            val intent = Intent(context, CardDetailActivity::class.java)
            if (clearTop!!) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            context.startActivity(intent)
            animateTransition(context)
        }

        private fun animateTransition(activity: Activity) {
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }
}
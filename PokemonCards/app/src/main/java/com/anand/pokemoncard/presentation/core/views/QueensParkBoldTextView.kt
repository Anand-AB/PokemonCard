package com.anand.pokemoncard.presentation.core.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.anand.pokemoncard.presentation.utility.AppConstants.Companion.QUEENS_PARK_TTF

public class QueensParkBoldTextView : AppCompatTextView {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        val font: Typeface = Typeface.createFromAsset(context.assets, QUEENS_PARK_TTF)
        this.setTypeface(font, Typeface.BOLD)
    }
}
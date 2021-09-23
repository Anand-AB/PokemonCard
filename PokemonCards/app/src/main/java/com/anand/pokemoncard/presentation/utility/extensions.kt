package com.anand.pokemoncard.presentation.utility

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import com.anand.pokemoncard.presentation.core.BaseActivity
import com.anand.pokemoncard.presentation.utility.AppConstants.Companion.TEXT_APP
import com.anand.pokemoncard.presentation.utility.AppConstants.Companion.TEXT_OK
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

fun AppCompatImageView.loadImage(image: Any) {
    Glide.with(this.context)
        .load(image)
        .into(this)
}

fun AppCompatImageView.loadImageRoundCorner(image: Any) {

    var requestOptions = RequestOptions()
    requestOptions = requestOptions.transforms(RoundedCorners(25))

    Glide.with(this.context)
        .load(image)
        .apply(requestOptions)
        //.placeholder(R.drawable.placeholder_square)
        .into(this)
}

fun Context.showDialog(
    title: String? = TEXT_APP,
    msg: String,
    positiveText: String? = TEXT_OK,
    listener: DialogInterface.OnClickListener? = null,
    icon: Int? = null
) {
    if (BaseActivity.dialogShowing) {
        return
    }
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setMessage(msg)
    builder.setCancelable(false)
    builder.setPositiveButton(positiveText) { dialog, which ->
        BaseActivity.dialogShowing = false
        listener?.onClick(dialog, which)
    }
    if (icon != null) {
        builder.setIcon(icon)
    }
    builder.create().show()
    BaseActivity.dialogShowing = true
}

fun Activity.isNetworkConnected(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (cm?.activeNetworkInfo?.isConnected == true) {
        return true
    }
    return false
}
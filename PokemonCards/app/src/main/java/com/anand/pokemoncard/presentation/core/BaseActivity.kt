package com.anand.pokemoncard.presentation.core

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.anand.pokemoncard.R
import com.anand.pokemoncard.domain.exceptions.MyException
import com.anand.pokemoncard.presentation.core.views.CustomProgressDialog
import com.anand.pokemoncard.presentation.core.views.CustomProgressWithMessageDialog
import com.anand.pokemoncard.presentation.utility.showDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Created by Anand A <anandabktda@gmail.com>
 * */

abstract class BaseActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    private var progress: CustomProgressDialog? = null
    private var progressMessage: CustomProgressWithMessageDialog? = null
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    abstract fun getBaseViewModel(): BaseViewModel?
    abstract fun getLayout(): Int

    companion object {
        var dialogShowing = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()

        progress =
            CustomProgressDialog(
                this
            )
        progressMessage =
            CustomProgressWithMessageDialog(
                this
            )

        setContentView(getLayout())
        initiation()
        setOnClickListener()
        getApiResponse()

        getBaseViewModel()?.exceptionLiveData?.observe(this, Observer {
            hideProgress()

            it?.apply {
                when (this) {
                    is MyException.InternetConnectionException -> {
                        showDialog(
                            msg = getString(R.string.error_network),
                            listener = DialogInterface.OnClickListener { dialog, _ ->
                                dialog.dismiss()
                            })
                    }
                    is MyException.JsonException -> {

                        showDialog(
                            msg = getString(R.string.error_unparceble_data),
                            listener = DialogInterface.OnClickListener { dialog, _ ->
                                dialog.dismiss()
                            })
                    }
                    else -> {
                        var message = it.message
                        message = if (message?.isEmpty() == true) {
                            getString(R.string.error_unknown_error) + it.localizedMessage
                        } else
                            getString(R.string.error_something_went_Wrong)
                        showDialog(
                            msg = message,
                            listener = DialogInterface.OnClickListener { dialog, _ ->
                                dialog.dismiss()
                            })
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    fun showProgress() {
        if (!this.isFinishing) {
            progress?.show()
        }
    }

    fun hideProgress() {
        if (!this.isFinishing && progress?.isShowing == true) {
            progress?.dismiss()
        }
    }

    fun toast(message: String?) {
        Toast.makeText(this@BaseActivity, message, Toast.LENGTH_LONG).show()
    }


    open fun setOnClickListener() {}
    open fun initiation() {}
    open fun getApiResponse() {}
}
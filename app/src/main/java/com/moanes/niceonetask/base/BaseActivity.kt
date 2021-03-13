package com.moanes.niceonetask.base

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

abstract class BaseActivity : AppCompatActivity() {
    private lateinit var progressDialog: ProgressDialog

    @LayoutRes
    abstract fun getLayout(): Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        progressDialog = ProgressDialog(this)

    }

    open fun showLoading() {
        progressDialog.show()
    }

    open fun hideLoading() {
        progressDialog.hide()
    }


    fun handleProgress(viewModel: BaseViewModel, swipeRefreshLayout: SwipeRefreshLayout? = null) {
        viewModel.showLoading.observe(this, {

            swipeRefreshLayout?.let { swipeRefreshLayout ->
                swipeRefreshLayout.isRefreshing = it
            }

            if (it)
                showLoading()
            else
                hideLoading()
        })
    }

    fun handleError(viewModel: BaseViewModel) {
        viewModel.errorLiveData.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroy() {
        if (progressDialog != null && progressDialog.isShowing) {
            progressDialog.cancel()
        }
        super.onDestroy()
    }
}
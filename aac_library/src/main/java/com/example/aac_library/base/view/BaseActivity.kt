package com.example.aac_library.base.view

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.example.aac_library.base.interf.IBaseView
import com.example.aac_library.base.interf.IIBaseView
import com.sunchen.netbus.NetStatusBus


abstract class BaseActivity : AppCompatActivity(), IBaseView {
    private var progressDialog: ProgressDialog? = null

    private val context: BaseActivity
        get() = this


    val lifecycleOwner: LifecycleOwner
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initLayoutResId())
        initDataBinding()
        initViewModelEvent()
        initView()
        initData()
    }


    protected abstract fun initLayoutResId(): Int
    protected abstract fun initView()

    protected abstract fun initData()
    protected abstract fun initDataBinding()

    override fun startLoading() {
        startLoading("")
    }

    override fun startLoading(msg: String) {

        if (progressDialog != null) {
            progressDialog!!.setTitle(msg)
            progressDialog!!.show()
        } else {
            progressDialog = ProgressDialog(this)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setCanceledOnTouchOutside(false)
        }
    }

    override fun dismissLoading() {

        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }

    }

    override fun showToast(msg: String) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    public override fun onStart() {
        super.onStart()
        NetStatusBus.getInstance().register(this)
    }

    public override fun onStop() {
        super.onStop()
        NetStatusBus.getInstance().unregister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissLoading()
    }
}

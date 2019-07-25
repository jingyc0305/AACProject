package com.example.aac_library.base.view

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.aac_library.base.BaseViewModel
import com.example.aac_library.base.event.BaseEvent
import com.example.aac_library.base.interf.IBaseView
import com.sunchen.netbus.NetStatusBus
import java.util.ArrayList

abstract class BaseActivity : AppCompatActivity(), IBaseView {
    private val context: BaseActivity
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

    protected abstract fun initViewModel(): BaseViewModel?
    /**
     * 暂时没有多个view model的情况
     */
    private fun initViewModels():List<BaseViewModel>?{
        return null
    }
    override fun startLoading() {
        startLoading("")
    }

    override fun startLoading(msg: String) {
    }

    override fun dismissLoading() {
    }

    override fun showToast(msg: String) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    private fun initViewModelEvent() {
        val viewModels = initViewModels()
        if (viewModels != null && viewModels.isNotEmpty()) {
            observeEvent(viewModels)
        } else {
            val viewModel = initViewModel()
            if (viewModel != null) {
                val modelList = ArrayList<BaseViewModel>()
                modelList.add(viewModel)
                observeEvent(modelList)
            }
        }

    }

    private fun observeEvent(viewModels: List<BaseViewModel>) {

        for (viewModel in viewModels) {

            viewModel.actionLiveData.observe(this, Observer{ baseActionEvent ->
                baseActionEvent?.let {
                    when (it.action) {
                        BaseEvent.SHOW_LOADING_DIALOG -> startLoading(it.message!!)
                        BaseEvent.DISMISS_LOADING_DIALOG -> dismissLoading()
                        BaseEvent.SHOW_TOAST -> showToast(it.message!!)
                        BaseEvent.FINISH -> finish()
                        BaseEvent.FINISH_WITH_RESULT_OK -> finish()
                    }
                }

            })
        }
    }

    fun bindService(serviceClass: Class<*>,connection: ServiceConnection) {
        bindService(Intent(this, serviceClass), connection, Context.BIND_AUTO_CREATE)
    }
    fun unBindService(connection: ServiceConnection) {
        unbindService(connection)
    }
    fun reBindService(serviceClass: Class<*>,connection: ServiceConnection) {
        unbindService(connection)
        bindService(Intent(this, serviceClass), connection, Context.BIND_AUTO_CREATE)
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

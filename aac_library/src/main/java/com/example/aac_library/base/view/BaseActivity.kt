package com.example.aac_library.base.view

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.blankj.utilcode.util.ToastUtils
import com.example.aac_library.base.BaseViewModel
import com.example.aac_library.base.event.BaseEvent
import com.example.aac_library.base.interf.IBaseView
import com.example.aac_library.stateview.StatusClickListener
import com.example.aac_library.stateview.StatusLayout
import com.sunchen.netbus.NetStatusBus
import java.util.ArrayList

abstract class BaseActivity : AppCompatActivity(), IBaseView {
    private val context: BaseActivity
        get() = this
    private var mStatusLayout: StatusLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initLayoutResId())
        initStatusLayout()
        initDataBinding()
        initViewModelEvent()
        initView()
        register()
        initData()
    }

    /**
     * 抽象公用方法 子类自定义实现
     */
    protected abstract fun initLayoutResId(): Int
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun register()
    protected abstract fun unRegister()
    //可选
    protected abstract fun initDataBinding()
    protected abstract fun initViewModel(): BaseViewModel?
    /**
     * 暂时没有多个view model的情况
     */
    private fun initViewModels(): List<BaseViewModel>? {
        return null
    }

    override fun startLoading() {
        startLoading("")
    }

    override fun startLoading(msg: String) {
        mStatusLayout?.showLoadingLayout()
    }

    override fun dismissLoading() {
        mStatusLayout?.showContentLayout()
    }

    override fun showError(msg: String) {
        mStatusLayout?.showErrorLayout()
    }
    override fun showEmpty(){
        mStatusLayout?.showEmptyLayout()
    }
    override fun showToast(msg: String) {
        ToastUtils.showShort(msg)
    }
    /**
     * 初始化视图状态组件
     */
    private fun initStatusLayout(){
        if (mStatusLayout == null) {
            mStatusLayout = StatusLayout.Builder(LayoutInflater.from(this).inflate(initLayoutResId(),null))
                .setOnStatusClickListener(object : StatusClickListener {
                    override fun onEmptyClick(view: View) {
                        //TODO 子类实现
                    }

                    override fun onErrorClick(view: View) {
                        //TODO 子类实现
                    }
                })
                .build()
        }
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

            viewModel.actionLiveData.observe(this, Observer { baseActionEvent ->
                baseActionEvent?.let {
                    when (it.action) {
                        BaseEvent.SHOW_LOADING_DIALOG -> startLoading(it.message!!)
                        BaseEvent.DISMISS_LOADING_DIALOG -> dismissLoading()
                        BaseEvent.SHOW_ERROR_DIALOG -> showError(it.message!!)
                        BaseEvent.SHOW_EMPTY -> showEmpty()
                        BaseEvent.SHOW_TOAST -> showToast(it.message!!)
                        BaseEvent.FINISH -> finish()
                        BaseEvent.FINISH_WITH_RESULT_OK -> finish()
                    }
                }

            })
        }
    }
    fun <T : BaseViewModel> getViewModelForActivity(fragment: AppCompatActivity, @NonNull modelClass: Class<T>): T {

        return ViewModelProviders.of(fragment).get(modelClass)

    }
    fun bindService(serviceClass: Class<*>, connection: ServiceConnection) {
        bindService(Intent(this, serviceClass), connection, Context.BIND_AUTO_CREATE)
    }

    fun unBindService(connection: ServiceConnection) {
        unbindService(connection)
    }

    fun reBindService(serviceClass: Class<*>, connection: ServiceConnection) {
        unbindService(connection)
        bindService(Intent(this, serviceClass), connection, Context.BIND_AUTO_CREATE)
    }

    private fun startActivity(activityClass: Class<*>) {
        startActivity(activityClass)
    }

    fun startActivity(activityClass: Class<*>, bundle: Bundle) {
        val intent = Intent()
        intent.setClass(this, activityClass)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    fun startActivityForResult(activityClass: Class<*>, bundle: Bundle, requestCode: Int) {
        val intent = Intent()
        intent.setClass(this, activityClass)
        intent.putExtras(bundle)
        startActivityForResult(intent, requestCode)
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
        unRegister()
    }
}

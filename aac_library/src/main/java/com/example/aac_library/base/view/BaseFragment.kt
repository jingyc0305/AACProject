package com.example.aac_library.base.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.aac_library.base.event.BaseActionEvent
import com.example.aac_library.base.interf.IView
import com.example.aac_library.base.interf.IViewModel

/**
 * @author: JingYuchun
 * @date: 2019/7/1 13:41
 * @desc: 基础Fragment
 */
abstract class BaseFragment:Fragment(),IView {
    /**
     * 视图是否初始化完成
     */
    private var isViewPrepare : Boolean = false
    /**
     * 是否加载过数据
     */
    private var isLoadedData:Boolean = false
    private var mRootView: View? = null//缓存fragment View
    override fun startLoading() {
    }

    override fun startLoading(msg: String?) {
    }

    override fun dismissLoading() {
    }

    override fun showToast(msg: String?) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }

    override fun finishWithResultOk() {
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewPrepare = true
        initView()
        initDataBinding()
        initViewModelEvent()
        //加载数据
        layzLoadData()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(initLayoutResId(),container,false)
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent则从parent删除，防止发生这个rootview已经有parent的错误。
        if(mRootView?.parent !=null){
            val mViewGroup = mRootView?.parent as ViewGroup
            mViewGroup.removeView(mRootView)
        }
        return mRootView
    }
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser){
            layzLoadData()
        }
    }
    /**
     * 懒加载
     */
    private fun layzLoadData(){
        if(userVisibleHint && isViewPrepare&&!isLoadedData){
            initData()
            isLoadedData = true
        }
    }
    private fun initViewModelEvent(){
        val viewModels = initViewModels()
        if (viewModels != null && viewModels?.size!! > 0) {
            observeEvent(viewModels)
        }else{
            val viewModel = initViewModel()
            if(null != viewModel){
                val viewModelList = arrayListOf<ViewModel>()
                viewModelList.add(viewModel)
                observeEvent(viewModelList)
            }
        }

        
    }
    private fun observeEvent(viewModels: List<ViewModel>?){
        viewModels?.forEach {
            if (it is IViewModel){
                val iViewModel = it as IViewModel
                iViewModel.actionLiveData.observe(this, Observer {
                    baseActionEvent->
                    run {
                        when (baseActionEvent.action) {
                            BaseActionEvent.SHOW_LOADING_DIALOG -> startLoading(baseActionEvent.message)
                            BaseActionEvent.DISMISS_LOADING_DIALOG -> dismissLoading()
                            BaseActionEvent.SHOW_TOAST -> showToast(baseActionEvent.message)
                        }
                    }
                })
            }
        }
    }

    /**
     * 初始化viewmodel
     */
    protected abstract fun initViewModel():ViewModel?

    /**
     * 初始化viewmodels
     */
    fun initViewModels(): List<ViewModel>? {
        return null
    }
    protected abstract fun initLayoutResId(): Int
    protected abstract fun initView()
    protected abstract fun initData()

    protected abstract fun initDataBinding()


    override fun onDestroy() {
        super.onDestroy()
        dismissLoading()

    }
}
package com.example.aac_library.base.view

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.aac_library.R
import com.example.aac_library.base.BaseViewModel
import com.example.aac_library.base.event.BaseEvent
import com.example.aac_library.base.interf.IBaseView
import com.example.aac_library.stateview.StatusClickListener
import com.example.aac_library.stateview.StatusLayout
import java.util.ArrayList

/**
 * @author: JingYuchun
 * @date: 2019/7/1 13:41
 * @desc: 基础Fragment
 */
abstract class BaseFragment : Fragment(), IBaseView {
    /**
     * 视图是否初始化完成
     */
    private var isViewPrepare: Boolean = false
    /**
     * 是否加载过数据
     */
    private var isLoadedData: Boolean = false
    private var mRootView: View? = null//缓存fragment View

    private var mStatusLayout: StatusLayout? = null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewPrepare = true
        initStatusLayout()
        initView()
        initDataBinding()
        initViewModelEvent()
        //加载数据
        layzLoadData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(initLayoutResId(), container, false)
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent则从parent删除，防止发生这个rootview已经有parent的错误。
        if (mRootView?.parent != null) {
            val mViewGroup = mRootView?.parent as ViewGroup
            mViewGroup.removeView(mRootView)
        }
        return mRootView
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            layzLoadData()
        }
    }

    /**
     * 懒加载
     */
    private fun layzLoadData() {
        if (userVisibleHint && isViewPrepare && !isLoadedData) {
            initData()
            isLoadedData = true
        }
    }

    private fun initStatusLayout(){
        if (mStatusLayout == null) {
            mStatusLayout = StatusLayout.Builder(mRootView!!)
                .setOnStatusClickListener(object : StatusClickListener {
                    override fun onEmptyClick(view: View) {
                        mStatusLayout?.showLoadingLayout()
                        Handler().postDelayed({ mStatusLayout?.showContentLayout() }, 3000)
                    }

                    override fun onErrorClick(view: View) {
                        mStatusLayout?.showLoadingLayout()
                        Handler().postDelayed({ mStatusLayout?.showEmptyLayout() }, 3000)
                    }
                })
                .build()
        }
    }
    protected abstract fun initLayoutResId(): Int
//    protected abstract fun initRootView(): View

    protected abstract fun initView()
    protected abstract fun initData()
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


    fun showError(){
        mStatusLayout?.showErrorLayout()
    }
    fun showEmpty(){
        mStatusLayout?.showEmptyLayout()
    }
    override fun showToast(msg: String) {

        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissLoading()

    }

    override fun finish() {
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
                        BaseEvent.SHOW_TOAST -> showToast(it.message!!)
                        BaseEvent.FINISH -> finish()
                        BaseEvent.FINISH_WITH_RESULT_OK -> finish()
                    }
                }

            })
        }
    }
}
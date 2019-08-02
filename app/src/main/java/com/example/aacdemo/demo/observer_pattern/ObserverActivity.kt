package com.example.aacdemo.demo.observer_pattern

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.animation.BaseAnimation
import com.example.aac_library.eventbus.LiveDataBus
import com.example.aac_library.stateview.StatusClickListener
import com.example.aac_library.stateview.StatusLayout
import com.example.aacdemo.R
import kotlinx.android.synthetic.main.activity_observer.*

/**
 * @author: JingYuchun
 * @date: 2019/6/27 11:13
 * @desc: 学习观察者模式
 */
class ObserverActivity : AppCompatActivity(), ObserverListener {
    private var message1 = "各位租客 房租涨价了200!"
    private var message2 = "各位租客 该交房租了,该交房租了!"
    private var adapter: ObserverAdapter? = null
    private var houseOwener: HouseOwener? = null
    private var statusLayout: StatusLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_observer)
        //初始化rv
        initRecycleView()
        statusLayout = StatusLayout.Builder(findViewById(R.id.ll_main)).setOnStatusClickListener(object :
            StatusClickListener {
            override fun onEmptyClick(view: View) {
            }

            override fun onErrorClick(view: View) {
            }
        }).build()
        //开始进行观察者事件
        initObserver()
        button_send.setOnClickListener {
            statusLayout?.showLoadingLayout()
            MyAsyncTask(houseOwener!!,message1,message2).execute()
        }
        LiveDataBus.get().with("show_list")?.observe(this, Observer {
            statusLayout?.showContentLayout()
            obser_recy.adapter = adapter
            adapter?.setNewData(it as MutableList<Message>?)
        })
    }

    private fun initRecycleView() {
        obser_recy.layoutManager = LinearLayoutManager(this)
        adapter = ObserverAdapter(mutableListOf())
        adapter?.openLoadAnimation(BaseQuickAdapter.SCALEIN)
        adapter?.isFirstOnly(false)
        adapter?.disableLoadMoreIfNotFullPage()
        adapter?.setOnLoadMoreListener({




        },obser_recy)
        obser_recy.adapter = adapter
    }

    private fun initObserver() {
        //创建被观察者-房东
        houseOwener = HouseOwener()

        //创建观察者-租客
        val tenant1 = Tenant("租客1", 1200)
        tenant1.observerListener = this
        val tenant2 = Tenant("租客2", 1700)
        tenant2.observerListener = this

        //订阅观察者-签订租房合同
        houseOwener?.registerObserver(tenant1)
        houseOwener?.registerObserver(tenant2)

    }

    override fun notifyChangeData(messageDatas: MutableList<Message>) {
        LiveDataBus.get().with("show_list")?.postValue(messageDatas)
    }

    class MyAsyncTask(private var houseOwener: HouseOwener, private var message1: String, private var message2: String) : AsyncTask<Void, Void, String>(){
        override fun doInBackground(vararg p0: Void?): String {
            //发布消息-房租涨价通知
            houseOwener?.notifyObserver(message1, ThingType.MESSAGE)
            //发布消息-交房租通知
            houseOwener?.notifyObserver(message2, ThingType.PAY)
            return ""
        }
    }
}
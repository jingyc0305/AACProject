package com.example.aacdemo.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.aac_library.eventbus.LiveDataBus
import com.example.aac_library.utils.image.ImageLoaderUtil
import com.example.aacdemo.R
import com.example.aacdemo.databinding.ActivityMainBinding
import com.example.aacdemo.demo.observer_pattern.ObserverActivity
import com.example.aacdemo.wanandroid.home.activity.HomeActivity
import com.example.roomlib.ui.MusicActivity
import com.example.roomlib.ui.MusicGreenDaoActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author: JingYuchun
 * @date: 2019年8月1日
 * @desc:示例demo
 */
class MainActivity : AppCompatActivity() {
    private lateinit var model: NameViewModel
    private lateinit var timerViewModel: TimerViewModel
    private val headIconUrl: String = "http://thumb10.jfcdns.com/2018-06/bce5b10ae530f530.png"
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //初始化数据绑定
        initDataBindnig()
        //初始化点击事件
        initClickEvent()
        //监听标题文字
        subscribTitle()
        //监听用户名字
        subscribNameChange()
        //监听倒计时更新UI
        subscribTimer()
        //LiveDataBus 接收
        LiveDataBus.get().with("change_name", String::class.java)?.observe(this, Observer { flag ->
            model.currentName.value = "昵称: $flag!"
        })
        //Glide加载器
        ImageLoaderUtil.get().loadCircleImage(this, imageView, headIconUrl)

    }

    private fun subscribTitle() {
        val titleObserver = Observer<String> {
            //更新ui
                newName ->
            hello.text = newName
        }
        val viewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)

        viewModel.name?.observe(this, titleObserver)

    }

    private fun initClickEvent() {
        button.setOnClickListener {
            startActivity(Intent(this, BusActivity::class.java))
        }
        obser_btn.setOnClickListener {
            startActivity(Intent(this, ObserverActivity::class.java))
        }
        wan_android.setOnClickListener {
            var tv:TextView? = null
            tv!!.text = ""

            startActivity(Intent(this, HomeActivity::class.java))
        }
        binder_btn.setOnClickListener {
            startActivity(Intent(this, ServiceActivity::class.java))
        }
        room_btn.setOnClickListener {
            startActivity(Intent(this, MusicActivity::class.java))
        }
        greendao_btn.setOnClickListener {
            startActivity(Intent(this, MusicGreenDaoActivity::class.java))
        }
    }

    private fun subscribNameChange() {
        model = ViewModelProviders.of(this).get(NameViewModel::class.java)
        val userNameObserver = Observer<String> {
            //更新ui
                newName ->
            data_bind_tv.text = newName
        }

        //监听名字变化
        model.currentName.observe(this, userNameObserver)
    }


    private fun initDataBindnig() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.user = User("JingChun", headIconUrl)

        binding.handles = MyHandlers()
    }

    private fun subscribTimer() {
        timerViewModel = ViewModelProviders.of(this).get(TimerViewModel::class.java)
        timerViewModel.elapsedTime.observe(this, Observer { time -> hello.text = "" + time })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}

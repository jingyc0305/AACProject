package com.example.aacdemo.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.aac_library.eventbus.LiveDataBus
import com.example.aac_library.utils.image.GlideImageLoader
import com.example.aac_library.utils.image.ImageLoaderUtil
import com.example.aacdemo.R
import com.example.aacdemo.asyntask.AsynActivity
import com.example.aacdemo.databinding.ActivityMainBinding
import com.example.aacdemo.demo.design_pattern.observer_pattern.ObserverActivity
import com.example.aacdemo.qrcode.QrCodeActivity
import com.example.aacdemo.wanandroid.home.activity.HomeActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {

    private val tag = "kt_tag_MainActivity"

    private lateinit var model: NameViewModel

    private lateinit var timerViewModel: TimerViewModel
    private val headIconUrl: String = "http://thumb10.jfcdns.com/2018-06/bce5b10ae530f530.png"
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //初始化数据绑定
        initDataBindnig()

        val nameObserver = Observer<String> {
            //更新ui
                newName ->
            hello.text = newName
        }
        val viewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)

        viewModel.name?.observe(this, nameObserver)

        model = ViewModelProviders.of(this).get(NameViewModel::class.java)


        //观察数据变化 更新UI
        model.currentName.observe(this, nameObserver)


        //手动改变数据 更新UI
        button.setOnClickListener {
            //model.currentName.value = "I am LiveData!~"

            //测试LiveDataBus
            sendMessage()
        }

        //观察数据变化 更新UI
        subscrib()

        really_btn.setOnClickListener { startActivity(Intent(this, ReallyActivity::class.java)) }
        qr_code_btn.setOnClickListener { startActivity(Intent(this, QrCodeActivity::class.java)) }
        anko_btn.onClick {
            startActivity<AnkoActivity>("param" to "anko")
        }
        asyn_btn.onClick {
            startActivity<AsynActivity>("param" to "asyn")
        }
        obser_btn.onClick {
            startActivity<ObserverActivity>("param" to "obser")
        }
        wan_android.onClick {
            startActivity<HomeActivity>("param" to "android")
        }
        binder_btn.onClick {
            startActivity<ServiceActivity>("param" to "binder")
        }
        //测试LiveDataBus
        LiveDataBus.get().with("isReceived",String::class.java)?.observe(this, Observer {
            flag -> viewModel.name?.value = "isReceived: $flag!"
        })


        //默认 Glide加载器
        ImageLoaderUtil.get().loadCircleImage(this,imageView,headIconUrl)

    }


    private fun sendMessage(){
        LiveDataBus.get().with("isReceived")?.value = "I'm from LiveDataBus Data"
    }
    private fun initDataBindnig() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.user = User("JingChun", headIconUrl)

        binding.handles = MyHandlers()
    }

    private fun subscrib() {
        timerViewModel = ViewModelProviders.of(this).get(TimerViewModel::class.java)
        timerViewModel.elapsedTime.observe(this, Observer { time -> hello.text = "" + time })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}

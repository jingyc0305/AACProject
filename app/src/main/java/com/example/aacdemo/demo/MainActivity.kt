package com.example.aacdemo.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.aacdemo.R
import com.example.aacdemo.databinding.ActivityMainBinding
import com.example.aacdemo.qrcode.QrCodeActivity
import kotlinx.android.synthetic.main.activity_main.*

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

        val viewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)

        //获取vm中的数据
        Log.d(tag, viewModel.name)

        model = ViewModelProviders.of(this).get(NameViewModel::class.java)

        val nameObserver = Observer<String> {
            //更新ui
                newName ->
            hello.text = newName
        }
        //观察数据变化 更新UI
        model.currentName.observe(this, nameObserver)


        //手动改变数据 更新UI
        button.setOnClickListener {
            model.currentName.value = "I am LiveData!~"
        }

        //观察数据变化 更新UI
        subscrib()

        really_btn.setOnClickListener { startActivity(Intent(this, ReallyActivity::class.java)) }
        qr_code_btn.setOnClickListener { startActivity(Intent(this, QrCodeActivity::class.java)) }
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

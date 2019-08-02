package com.example.aacdemo.weather

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.blankj.utilcode.util.GsonUtils
import com.example.aac_library.base.BaseViewModel
import com.example.aacdemo.R
import com.example.aac_library.base.view.BaseActivity
import com.sunchen.netbus.annotation.NetSubscribe
import com.sunchen.netbus.type.Mode
import kotlinx.android.synthetic.main.really_mvvm.*

class WeatherActivity : BaseActivity() {
    override fun register() {
    }

    override fun unRegister() {
    }

    private var weatherViewModel: WeatherViewModel? = null
    public override fun initViewModel(): BaseViewModel? {
        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java!!)
        weatherViewModel!!.lifecycleOwner = this
        weatherViewModel!!.weatherMutableLiveData.observe(this, Observer {
            showWeatherResult(it)
        })
        return weatherViewModel
    }

    override fun initLayoutResId(): Int {
        return R.layout.really_mvvm
    }

    override fun initView() {}

    override fun initData() {
        queryWeather("大连")
    }

    override fun initDataBinding() {

    }

    /**
     * 调用
     */
    private fun queryWeather(cityName: String) {
        weatherViewModel!!.getWeather(cityName)
    }

    /**
     * 处理请求结果
     * @param weather
     * @return
     */
    private fun showWeatherResult(weather: Weather) {
        val result = StringBuilder()
        for (nearestWeather in weather.data.weather) {
            result.append("\n\n").append(GsonUtils.getGson().toJson(nearestWeather))
        }

        textView.text = result.toString()
    }

    @NetSubscribe(mode = Mode.WIFI_CONNECT)
    fun doSometing() {
        Toast.makeText(this, "wifi 已连接", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "wifi 已连接")
    }

    @NetSubscribe(mode = Mode.NONE)
    fun netWorkDisConnect() {
        Toast.makeText(this, "当前无网络", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "当前无网络")
    }

    companion object {
        private const val TAG = "WeatherActivity"
    }
}

package com.example.aacdemo.demo;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import com.example.aacdemo.R;
import com.example.aac_library.base.view.BaseActivity;
import com.example.aacdemo.weather.Weather;
import com.example.aacdemo.weather.WeatherViewModel;
import com.google.gson.Gson;
import com.sunchen.netbus.annotation.NetSubscribe;
import com.sunchen.netbus.type.Mode;

public class ReallyActivity extends BaseActivity {
    private static final String TAG = "ReallyActivity";
    private WeatherViewModel weatherViewModel;
    @Override
    protected ViewModel initViewModel() {
        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        weatherViewModel.setLifecycleOwner(this);
        weatherViewModel.getWeatherMutableLiveData().observe(this, weather -> showWeatherResult(weather));
        return weatherViewModel;
    }

    @Override
    protected int initLayoutResId() {
        return R.layout.really_mvvm;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        queryWeather("大连");
    }

    @Override
    protected void initDataBinding() {

    }

    /**
     * 调用
     */
    private void queryWeather(String cityName){
        weatherViewModel.getWeather(cityName);
    }
    /**
     * 处理请求结果
     * @param weather
     * @return
     */
    public void showWeatherResult(Weather weather){
        TextView textView = findViewById(R.id.textView);
        StringBuilder result = new StringBuilder();
        for (Weather.InnerWeather.NearestWeather nearestWeather : weather.getData().getWeather()) {
            result.append("\n\n").append(new Gson().toJson(nearestWeather));
        }

        textView.setText(result.toString());
    }
    @NetSubscribe(mode = Mode.WIFI_CONNECT)
    public void doSometing() {
        Toast.makeText(this,"wifi 已连接",Toast.LENGTH_SHORT).show();
        Log.d(TAG, "wifi 已连接");
    }
    @NetSubscribe(mode = Mode.NONE)
    public void netWorkDisConnect(){
        Toast.makeText(this,"当前无网络",Toast.LENGTH_SHORT).show();
        Log.d(TAG,"当前无网络");
    }
}

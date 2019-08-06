package com.example.aacdemo.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import android.os.SystemClock
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import java.util.*


class TimerViewModel : ViewModel() {
    private val mElapsedTime = MutableLiveData<Long>()

    private val mInitialTime: Long = SystemClock.elapsedRealtime()
    private var timer: Timer? = null
    private var curTime: Long = 0L
    private var timerTask:TimerTask? = null
    private var startTime : Long = 0L
    val elapsedTime: LiveData<Long>
        get() = mElapsedTime

    fun timerStart() {
        startTime = SystemClock.elapsedRealtime()
        timer = Timer()
        timerTask = MyTimerTask(curTime,mInitialTime, mElapsedTime)
        // Update the elapsed time every second.
        timer!!.scheduleAtFixedRate(
            timerTask,
            ONE_SECOND,
            ONE_SECOND
        )

    }

    class MyTimerTask(var lastTime:Long, private var mInitialTime: Long, var mElapsedTime: MutableLiveData<Long>) : TimerTask() {
        override fun run() {
            var newValue = (SystemClock.elapsedRealtime() - mInitialTime - lastTime) / 1000
            if (lastTime > 0)
                newValue += 1L
            // setValue() cannot be called from a background thread so post to main thread.
            mElapsedTime.postValue(newValue)
        }
    }

    fun timerCancle() {
        timer?.cancel()
        timer = null
        timerTask?.cancel()
        timerTask = null
    }

    fun timerPause(): Long {
        curTime = SystemClock.elapsedRealtime() - startTime
        timerCancle()
        return curTime
    }

    companion object {
        private const val ONE_SECOND = 1000L
    }
}
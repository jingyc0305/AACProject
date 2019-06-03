package com.example.aacdemo.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import android.os.SystemClock
import androidx.lifecycle.MutableLiveData
import java.util.*


class TimerViewModel : ViewModel() {
    private val mElapsedTime = MutableLiveData<Long>()

    private val mInitialTime: Long = SystemClock.elapsedRealtime()

    val elapsedTime: LiveData<Long>
        get() = mElapsedTime

    init {
        val timer = Timer()

        // Update the elapsed time every second.
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val newValue = (SystemClock.elapsedRealtime() - mInitialTime) / 1000
                // setValue() cannot be called from a background thread so post to main thread.
                mElapsedTime.postValue(newValue)
            }
        },
            ONE_SECOND,
            ONE_SECOND
        )

    }

    companion object {
        private const val ONE_SECOND = 1000L
    }
}
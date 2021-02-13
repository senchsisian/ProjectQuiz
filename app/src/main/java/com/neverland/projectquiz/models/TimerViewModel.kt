package com.neverland.projectquiz.models

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {
    private var _liveTimerInfo = MutableLiveData<Long>()
    var liveTimerInfo = _liveTimerInfo
    var secondsCount = 15L
    private var timer = object : CountDownTimer(secondsCount * 1000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            liveTimerInfo.postValue(millisUntilFinished / 1000)

        }

        override fun onFinish() {
            Log.v("Timer log. ", "$liveTimerInfo")
        }
    }

    fun start() {
        timer.start()
    }
}
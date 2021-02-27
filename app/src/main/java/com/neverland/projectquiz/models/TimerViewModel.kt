package com.neverland.projectquiz.models

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {
    private var _liveTimerInfo = MutableLiveData<Long>()
    var liveTimerInfo = _liveTimerInfo
    var secondsCount = 16L
    var timer: CountDownTimer? = null

    fun start() {
        timer?.cancel()
        _liveTimerInfo.value = secondsCount
        timer = object : CountDownTimer(secondsCount * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                liveTimerInfo.postValue(millisUntilFinished / 1000)
            }
            override fun onFinish() {
                liveTimerInfo.postValue(0L)
            }
        }
        timer?.start()
    }
}
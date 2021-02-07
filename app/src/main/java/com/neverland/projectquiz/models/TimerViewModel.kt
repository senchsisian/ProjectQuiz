package com.neverland.projectquiz.models

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel :ViewModel() {
    private var _liveTimerInfo=MutableLiveData<Long>()
    var liveTimerInfo=_liveTimerInfo
    var secondsCount=15L
    private val timer=object:CountDownTimer(secondsCount*1000, 1000){
        override fun onTick(millisUntilFinished: Long) {
            _liveTimerInfo.postValue(millisUntilFinished/1000)
        }

        override fun onFinish() {
        }
    }
    fun start(){timer.start()}
}
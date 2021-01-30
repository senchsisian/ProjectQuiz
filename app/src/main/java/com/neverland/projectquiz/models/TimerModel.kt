package com.neverland.projectquiz.models

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerModel :ViewModel() {
    var timerValue=MutableLiveData<Long>()
    private val timer=object:CountDownTimer(10000, 1000){
    override fun onTick(millisUntilFinished: Long) {
        timerValue.postValue(millisUntilFinished/1000)
    }

    override fun onFinish() {
    }
}
    fun start(){timer.start()}
}
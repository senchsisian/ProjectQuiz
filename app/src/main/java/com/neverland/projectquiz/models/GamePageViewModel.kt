package com.neverland.projectquiz.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neverland.projectquiz.database.DataModel
import com.neverland.projectquiz.repo.DataProviderRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GamePageViewModel : ViewModel() {
    private val _liveDataGameInfo = MutableLiveData<List<DataModel>>()
    val liveDataGameInfo: LiveData<List<DataModel>> = _liveDataGameInfo

    fun getDataFromDB(column: String) {
        GlobalScope.launch(Dispatchers.IO) {
            _liveDataGameInfo.postValue(DataProviderRepo.getDataFromDB(column))
        }
    }
}
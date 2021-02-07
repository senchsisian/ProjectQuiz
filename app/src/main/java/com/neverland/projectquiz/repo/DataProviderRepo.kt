package com.neverland.projectquiz.repo

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.neverland.projectquiz.database.DataDB
import com.neverland.projectquiz.database.DataModel
import com.neverland.projectquiz.database.GET_DATA

@SuppressLint("StaticFieldLeak")
object DataProviderRepo {

    private var context: Context? = null
    var dataDB: DataDB? = null

    @SuppressLint("StaticFieldLeak")
    fun setContextAndInitDb(newContext: Context) {
        context = newContext

        dataDB = Room.databaseBuilder(newContext, DataDB::class.java, GET_DATA).build()

    }

    suspend fun getDataFromDB(): List<DataModel>? {
        val getDataList = dataDB?.getDataDao()?.getAllData()
        Log.v("Getting elements in game page", getDataList.toString())
        return getDataList
    }
}

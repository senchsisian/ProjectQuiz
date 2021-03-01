package com.neverland.projectquiz.repo

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.neverland.projectquiz.*
import com.neverland.projectquiz.database.DataDB
import com.neverland.projectquiz.database.DataModel

object DataProviderRepo {
    private var dataDB: DataDB? = null

    fun setContextAndInitDb(newContext: Context) {
        dataDB = Room.databaseBuilder(newContext, DataDB::class.java, GET_DATA).build()
    }

    fun getDataFromDB(column: String): List<DataModel>? {
        val getDataList = when (column) {
            FACTS_OF_HISTORY-> dataDB?.getDataDao()?.getFactsOfHistoryData()
            KINGDOM_OF_VAN -> dataDB?.getDataDao()?.getKingdomOfVanData()
            YERVANDUNIS_FAMILY -> dataDB?.getDataDao()?.getYervandunisData()
            ARSHAKUNIS_FAMILY -> dataDB?.getDataDao()?.getArshakunisData()
            ARTASHESYANS_FAMILY -> dataDB?.getDataDao()?.getArtashesyansData()
            BAGRATUNIS_FAMILY -> dataDB?.getDataDao()?.getBagratunisData()
            else -> dataDB?.getDataDao()?.getFactsOfHistoryData()
        }
        Log.v("Getting elements in game page", getDataList.toString())
        return getDataList
    }
}

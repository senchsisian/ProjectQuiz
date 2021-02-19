package com.neverland.projectquiz.repo

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.neverland.projectquiz.ARSHAKUNIS_FAMILY
import com.neverland.projectquiz.ARTASHESYANS_FAMILY
import com.neverland.projectquiz.GET_DATA
import com.neverland.projectquiz.KINGDOM_OF_VAN
import com.neverland.projectquiz.database.DataDB
import com.neverland.projectquiz.database.DataModel

object DataProviderRepo {
    private var dataDB: DataDB? = null

    fun setContextAndInitDb(newContext: Context) {
        dataDB = Room.databaseBuilder(newContext, DataDB::class.java, GET_DATA).build()
    }

    fun getDataFromDB(column: String): List<DataModel>? {
        val getDataList = when (column) {
            KINGDOM_OF_VAN -> dataDB?.getDataDao()?.getKingdomOfVanData()
            ARSHAKUNIS_FAMILY -> dataDB?.getDataDao()?.getArshakunisData()
            ARTASHESYANS_FAMILY -> dataDB?.getDataDao()?.getArtashesyansData()
            ARTASHESYANS_FAMILY -> dataDB?.getDataDao()?.getBagratunisData()
            else -> dataDB?.getDataDao()?.getKingdomOfVanData()
        }
        Log.v("Getting elements in game page", getDataList.toString())
        return getDataList
    }
}

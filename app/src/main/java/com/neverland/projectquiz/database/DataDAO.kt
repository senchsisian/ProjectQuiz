package com.neverland.projectquiz.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.neverland.projectquiz.*

@Dao
interface DataDAO {
    @Insert
    fun addData(vararg data: DataModel)

    @Query("$SELECT_FROM $GET_DATA $ORDER_BY")
    fun getAllData(): List<DataModel>

    //irakanacnum e tvyalneri kardacum yst vikrotinayi bajinneri
    @Query("$SELECT_FROM $GET_DATA $WHERE_COLUMN\"$FACTS_OF_HISTORY\" $ORDER_BY")
    fun getFactsOfHistoryData(): List<DataModel>

    @Query("$SELECT_FROM $GET_DATA $WHERE_COLUMN\"$KINGDOM_OF_VAN\" $ORDER_BY")
    fun getKingdomOfVanData(): List<DataModel>

    @Query("$SELECT_FROM $GET_DATA $WHERE_COLUMN\"$YERVANDUNIS_FAMILY\" $ORDER_BY")
    fun getYervandunisData(): List<DataModel>

    @Query("$SELECT_FROM $GET_DATA $WHERE_COLUMN\"$ARSHAKUNIS_FAMILY\" $ORDER_BY")
    fun getArshakunisData(): List<DataModel>

    @Query("$SELECT_FROM $GET_DATA $WHERE_COLUMN\"$ARTASHESYANS_FAMILY\" $ORDER_BY")
    fun getArtashesyansData(): List<DataModel>

    @Query("$SELECT_FROM $GET_DATA $WHERE_COLUMN\"$BAGRATUNIS_FAMILY\" $ORDER_BY")
    fun getBagratunisData(): List<DataModel>
    //

    @Delete
    fun removeData(vararg data: DataModel)

    @Query("$DELETE_FROM $GET_DATA")
    fun deleteAll()
}
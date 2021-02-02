package com.neverland.projectquiz.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

const val SELECT_FROM="SELECT * FROM"
const val ORDER_BY="ORDER BY ID"
const val DELETE_FROM="DELETE FROM"

@Dao
interface DataDAO {
    @Insert
    fun addData(vararg data: DataModel)

    @Query("$SELECT_FROM $GET_DATA $ORDER_BY")
    fun getAllData():List<DataModel>

    @Delete
    fun removeData(vararg data: DataModel)

    @Query("$DELETE_FROM $GET_DATA")
    fun deleteAll()
}
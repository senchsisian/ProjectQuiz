package com.neverland.projectquiz.database

import androidx.room.Entity
import androidx.room.PrimaryKey

const val GET_DATA="firebase_data"

@Entity(tableName = GET_DATA)
data class DataModel(@PrimaryKey(autoGenerate = false) var idKey:Int,
                     var id:Int,
                     var question:String,
                     var answer1:String,
                     var answer2:String,
                     var answer3:String,
                     var answer4:String,
                     var answerRight:String)
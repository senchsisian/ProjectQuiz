package com.neverland.projectquiz.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.neverland.projectquiz.GET_DATA

@Entity(tableName = GET_DATA)
data class DataModel(
    @PrimaryKey(autoGenerate = false) var idKey: Int,
    var column: String,
    var id: Int,
    var question: String,
    var answer1: String,
    var answer2: String,
    var answer3: String,
    var answer4: String,
    var answerRight: String
)
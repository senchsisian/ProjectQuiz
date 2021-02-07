package com.neverland.projectquiz.database

import androidx.room.Database
import androidx.room.RoomDatabase

const val DATABASE_VERSION=5

@Database(entities = [DataModel::class], version = DATABASE_VERSION)
abstract class DataDB : RoomDatabase() {
    abstract  fun  getDataDao():DataDAO
}
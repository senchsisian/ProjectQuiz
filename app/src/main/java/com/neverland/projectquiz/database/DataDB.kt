package com.neverland.projectquiz.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.neverland.projectquiz.DATABASE_VERSION

@Database(entities = [DataModel::class], version = DATABASE_VERSION)
abstract class DataDB : RoomDatabase() {
    abstract fun getDataDao(): DataDAO
}
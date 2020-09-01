package com.example.mytask.RoomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mytask.model.Productfile

@Database(entities = [Productfile::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao?
}
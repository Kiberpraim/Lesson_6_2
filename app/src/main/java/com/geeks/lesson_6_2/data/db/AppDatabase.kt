package com.geeks.lesson_6_2.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geeks.lesson_6_2.data.model.Task

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
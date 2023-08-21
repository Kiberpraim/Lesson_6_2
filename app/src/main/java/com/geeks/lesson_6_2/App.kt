package com.geeks.lesson_6_2

import android.app.Application
import androidx.room.Room
import com.geeks.lesson_6_2.data.db.AppDatabase

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "Task-file"
        ).allowMainThreadQueries().build()
    }

    companion object {
        lateinit var db: AppDatabase
    }

}
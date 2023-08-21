package com.geeks.lesson_6_2.di

import android.content.Context
import androidx.room.Room
import com.geeks.lesson_6_2.data.db.AppDatabase
import com.geeks.lesson_6_2.data.db.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "Love-file")
            .allowMainThreadQueries().build()
    }

    @Provides
    fun provideLoveDao(@ApplicationContext context: Context): TaskDao {
        return provideAppDatabase(context).taskDao()
    }
}
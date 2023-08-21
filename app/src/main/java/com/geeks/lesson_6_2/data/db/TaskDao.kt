package com.geeks.lesson_6_2.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.geeks.lesson_6_2.data.model.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getAll(): List<Task>

    @Insert
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("SELECT * FROM tasks WHERE checkBox = 0")
    fun getTasksFalse(): List<Task>

    @Query("SELECT * FROM tasks WHERE checkBox = 1")
    fun getTasksTrue(): List<Task>
}
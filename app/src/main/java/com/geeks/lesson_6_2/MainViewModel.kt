package com.geeks.lesson_6_2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geeks.lesson_6_2.data.model.Task

class MainViewModel : ViewModel() {

    private val dao = App.db.taskDao()

    private var tasks = dao.getAll().toMutableList()
    private var _list = MutableLiveData(tasks)
    val list: LiveData<MutableList<Task>> = _list

    fun getTasks() {
        tasks = dao.getAll().toMutableList()
        _list.value = tasks
        Log.e("kiber", "getTask -> ${list.value}")
    }

    fun addTask(task: Task) {
        dao.insert(task)
    }

    fun updateTask(task: Task) {
        dao.update(task)
    }

    fun deleteTask(task: Task) {
        dao.delete(task)
    }

    fun checkedTask(task: Task, isChecked: Boolean) {

        val data = task.copy(
            checkBox = isChecked
        )

        Log.e("kiber","isCheck -> $data")

        dao.update(data)

    }

    fun filterTasksFalse() {
        tasks = dao.getTasksFalse().toMutableList()
        _list.value = tasks
        Log.e("kiber","isTaskFalse -> ${_list.value}")
    }

    fun filterTasksTrue() {
        tasks = dao.getTasksTrue().toMutableList()
        _list.value = tasks
        Log.e("kiber","isTaskTrue -> ${_list.value}")
    }
}
package com.geeks.lesson_6_2.ui.main

import android.R
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.geeks.lesson_6_2.MainViewModel
import com.geeks.lesson_6_2.databinding.ActivityMainBinding
import com.geeks.lesson_6_2.data.model.Task
import com.geeks.lesson_6_2.ui.task.TaskActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private val adapter = TaskAdapter(
        this::onLongClickTask,
        this::isCheckedTask,
        this::onClickTask,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.recyclerView.adapter = adapter

        initView()
        initClick()
        initSpinner()
    }

    private fun initClick() {
        binding.fab.setOnClickListener {
            val intent = Intent(this, TaskActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initView() {
        viewModel.list.observe(this) { updatedList ->
            binding.recyclerView.adapter = adapter

            adapter.setList(updatedList)
        }
    }

    private fun onLongClickTask(task: Task) { // deleteTaskClick
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle(getString(com.geeks.lesson_6_2.R.string.are_you_wont_delete))
            .setMessage(getString(com.geeks.lesson_6_2.R.string.alertdialog_message))
            .setPositiveButton(getString(com.geeks.lesson_6_2.R.string.ok)) { dialog: DialogInterface, _: Int ->
                // Обработка нажатия кнопки "OK"
                viewModel.deleteTask(task)
                adapter.delete(task)
                dialog.dismiss()
            }
            .setNegativeButton(getString(com.geeks.lesson_6_2.R.string.cancel)) { dialog: DialogInterface, _: Int ->
                // Обработка нажатия кнопки "Отмена"
                dialog.dismiss()
            }
        dialogBuilder.show()
    }

    private fun onClickTask(task: Task) {
        val intent = Intent(this, TaskActivity::class.java)
        intent.putExtra(UPDATE_TASK_KEY, task)
        startActivity(intent)
    }

    private fun isCheckedTask(task: Task, isChecked: Boolean = false) {
        viewModel.checkedTask(task, isChecked)
    }

    private fun initSpinner() {
        val taskFilterList = arrayOf(getString(com.geeks.lesson_6_2.R.string.all_task),
            getString(com.geeks.lesson_6_2.R.string.false_task),
            getString(com.geeks.lesson_6_2.R.string.true_task))

        val adapterSpinner = ArrayAdapter(this, R.layout.simple_spinner_item, taskFilterList)
        adapterSpinner.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        binding.spinner.adapter = adapterSpinner

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                // Здесь вы можете выполнить действия на основе выбранного элемента
                when (taskFilterList[position]) {
                    getString(com.geeks.lesson_6_2.R.string.all_task) -> viewModel.getTasks()
                    getString(com.geeks.lesson_6_2.R.string.false_task) -> viewModel.filterTasksFalse()
                    getString(com.geeks.lesson_6_2.R.string.true_task) -> viewModel.filterTasksTrue()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

                // Обработка, если ничего не выбрано
                viewModel.getTasks()
            }
        }
    }

    companion object {
        const val UPDATE_TASK_KEY = "update_task.key"
    }

    override fun onResume() {
        super.onResume()
        initSpinner()
    }
}

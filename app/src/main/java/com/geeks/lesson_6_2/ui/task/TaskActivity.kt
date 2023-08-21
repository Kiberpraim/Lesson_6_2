package com.geeks.lesson_6_2.ui.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.geeks.lesson_6_2.MainViewModel
import com.geeks.lesson_6_2.R
import com.geeks.lesson_6_2.data.model.Task
import com.geeks.lesson_6_2.databinding.ActivityTaskBinding
import com.geeks.lesson_6_2.ui.main.MainActivity

class TaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskBinding
    private lateinit var viewModel: MainViewModel

    private var task: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        task = intent.getSerializableExtra(MainActivity.UPDATE_TASK_KEY) as Task?

        if (task == null){
            createTask()
        }else{
            initView()
        }
    }

    private fun createTask() {
        binding.btnSave.setOnClickListener {

            val data = Task(
                title = binding.etTitle.text.toString(),
                description = binding.etDescription.text.toString(),
            )

            viewModel.addTask(data)

            finish()
        }
    }

    private fun initView() {
        with(binding) {
            etTitle.setText(task?.title)
            etDescription.setText(task?.description)
            btnSave.text = getString(R.string.update)

            btnSave.setOnClickListener {

                val data = task!!.copy(
                    title = binding.etTitle.text.toString(),
                    description = binding.etDescription.text.toString(),
                )

                viewModel.updateTask(data)

                finish()
            }
        }
    }
}

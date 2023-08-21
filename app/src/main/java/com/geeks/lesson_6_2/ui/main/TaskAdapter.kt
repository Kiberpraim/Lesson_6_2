package com.geeks.lesson_6_2.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.geeks.lesson_6_2.databinding.ItemTaskBinding
import com.geeks.lesson_6_2.data.model.Task

class TaskAdapter(
    private val onLongClickTask: (Task) -> Unit, // deleteTaskClick
    private val isCheckedTask: (Task, Boolean) -> Unit,
    private val onClickTask: (Task) -> Unit,
) : Adapter<TaskAdapter.TaskViewHolder>() {

    private var list = mutableListOf<Task>()

    fun setList(newList: MutableList<Task>) {
        this.list = newList
        notifyDataSetChanged()
    }

    fun delete(task: Task) {
        list.remove(task)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder =
        TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.binds(list[position])
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : ViewHolder(binding.root) {
        fun binds(task: Task) = with(binding) {
            tvTitle.text = task.title
            tvDescription.text = task.description
            checkBox.isChecked = task.checkBox

            itemView.setOnLongClickListener {
                onLongClickTask(task) // deleteTaskClick(task)
                true
            }

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                isCheckedTask(task, isChecked)
                Log.e("kiber" , "Adapter item check -> $task \t $isChecked")
            }

            itemView.setOnClickListener {
                onClickTask(task)
            }
        }
    }
}

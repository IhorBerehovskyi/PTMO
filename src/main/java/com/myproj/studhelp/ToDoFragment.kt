package com.myproj.studhelp

import android.annotation.SuppressLint
import android.app.PendingIntent.OnFinished
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.myproj.studhelp.databinding.FragmentToDoBinding

class ToDoFragment : Fragment() {

    private lateinit var dbHelper: DbHelper
    private lateinit var editTextForm: EditText
    private lateinit var currentList: ListView
    private lateinit var finishedList: ListView
    private lateinit var adapter: TaskAdapter

    //val currentData = mutableListOf<Task>()
    //val finishedData = mutableListOf<Task>()

    //private var _binding: FragmentToDoBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentToDoBinding.inflate(inflater)

        dbHelper = DbHelper(requireContext())

        editTextForm = binding.editTextForm
        currentList = binding.currentTasksList
        finishedList = binding.finishedTasksList
        updateData()
        //binding.currentTasksList =
        binding.addTaskButton.setOnClickListener {
            addSubjectToDatabase()
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ToDoFragment()
    }

    private fun addSubjectToDatabase() {
        val taskText = editTextForm.text.toString()

        if (taskText.isNotBlank()) {
            val task = Task(
                status = 0,
                taskLabel = taskText
            )

            val insertedId = dbHelper.insertTask(task)
            if (insertedId != -1L) {
                Toast.makeText(requireContext(), "Task added successfully", Toast.LENGTH_SHORT).show()
                updateData()
                editTextForm.text.clear()
            } else {
                Toast.makeText(requireContext(), "Error adding task", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Task label cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("Range")
    private fun getTasksFromDatabase(stat: Int): List<Task> {
        val currentList = mutableListOf<Task>()
        val cursor = dbHelper.getAllData()

        while (cursor.moveToNext()) {
            val task = Task(
                id = cursor.getLong(cursor.getColumnIndex(DbHelper.COLUMN_ID)),
                status = cursor.getInt(cursor.getColumnIndex(DbHelper.COLUMN_STATUS)),
                taskLabel = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_TASK_LABEL))
            )
            if(task.status == stat){
                currentList.add(task)
            }
        }
        cursor.close()
        return currentList
    }

    private fun updateData() {

        adapter = TaskAdapter(requireContext(), R.layout.current_task_item, getTasksFromDatabase(0))
        currentList.adapter = adapter
        adapter = TaskAdapter(requireContext(), R.layout.current_task_item, getTasksFromDatabase(1))
        finishedList.adapter = adapter
    }
}
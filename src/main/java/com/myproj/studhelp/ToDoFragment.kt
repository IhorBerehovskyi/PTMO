package com.myproj.studhelp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.myproj.studhelp.databinding.FragmentToDoBinding

class ToDoFragment : Fragment() {

    private lateinit var dbHelper: DbHelper
    private lateinit var editText: EditText

    //private var _binding: FragmentToDoBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentToDoBinding.inflate(inflater)

        dbHelper = DbHelper(requireContext())

        editText = binding.editText
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
        val taskText = editText.text.toString()

        if (taskText.isNotBlank()) {
            val task = Task(
                status = false,
                taskLabel = taskText
            )

            val insertedId = dbHelper.insertSubject(task)
            if (insertedId != -1L) {
                Toast.makeText(requireContext(), "Task added successfully", Toast.LENGTH_SHORT).show()
                editText.text.clear()
            } else {
                Toast.makeText(requireContext(), "Error adding task", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Task label cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }
}
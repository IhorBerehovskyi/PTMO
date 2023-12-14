package com.myproj.studhelp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class StudentAdapter(private var studentList: List<Student>) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val surnameTextView: TextView = itemView.findViewById(R.id.surnameTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val mark: TextView = itemView.findViewById(R.id.marks)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val currentStudent = studentList[position]
        holder.surnameTextView.text = currentStudent.surname
        holder.nameTextView.text = currentStudent.name
        holder.mark.text = currentStudent.rating.roundToInt().toString()

    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    fun setStudentList(newList: List<Student>) {
        studentList = newList
        notifyDataSetChanged()
    }
}
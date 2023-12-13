package com.myproj.studhelp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class TaskAdapter(context: Context, resource: Int, objects: List<Task>) :
    ArrayAdapter<Task>(context, resource, objects) {

    private val dbHelper = DbHelper(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.current_task_item, parent, false)

        val task = getItem(position)

        val taskText: TextView = rowView.findViewById(R.id.itemView)
        val finishButton: ImageButton = rowView.findViewById(R.id.finishButton)

        taskText.text = "${task?.taskLabel}"

        finishButton.setOnClickListener {

            if (task!!.status == 0) {
                val rowsAffected = dbHelper.updateTask(task)

                if (rowsAffected > 0) {

                    remove(task)
                    notifyDataSetChanged()
                    Toast.makeText(context, "Task transferred", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }else{
                val rowsAffected = dbHelper.deleteTask(task.id)
                if (rowsAffected > 0) {
                    remove(task)
                    notifyDataSetChanged()
                    Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Error while deleting", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return rowView
    }
}

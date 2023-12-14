package com.myproj.studhelp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.myproj.studhelp.databinding.FragmentMapBinding
import com.myproj.studhelp.databinding.FragmentMarksBinding

class MarksFragment : Fragment() {

    private lateinit var binding: FragmentMarksBinding
    private lateinit var dbRef: DatabaseReference
    private val GROUP = "Students"

    private lateinit var recyclerView: RecyclerView
    private lateinit var studentAdapter: StudentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarksBinding.inflate(inflater)
        dbRef = FirebaseDatabase.getInstance().getReference(GROUP)

        recyclerView = binding.recyclerView
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        studentAdapter = StudentAdapter(ArrayList())
        recyclerView.adapter = studentAdapter

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val studentList = ArrayList<Student>()
                for (studentSnapshot in dataSnapshot.children) {
                    val student = studentSnapshot.getValue(Student::class.java)
                    student?.let { studentList.add(it) }
                }

                studentAdapter.setStudentList(studentList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Обробте помилку, якщо не вдалося отримати дані з Firebase
            }
        })

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = MarksFragment()
    }
}

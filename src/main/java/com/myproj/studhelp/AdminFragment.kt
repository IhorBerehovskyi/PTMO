package com.myproj.studhelp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import com.myproj.studhelp.databinding.FragmentAdminBinding

class AdminFragment : Fragment() {

    private lateinit var binding: FragmentAdminBinding

    private lateinit var studSpinner: Spinner
    private lateinit var studentsList: MutableList<Student>

    private lateinit var surname: EditText
    private lateinit var name: EditText
    private lateinit var fatherName: EditText
    private lateinit var APKS: EditText
    private lateinit var MZOSZ: EditText
    private lateinit var MOS: EditText
    private lateinit var PZIR: EditText
    private lateinit var PTMO: EditText
    private lateinit var TSD: EditText

    private lateinit var dbRef: DatabaseReference
    private val GROUP = "Students"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminBinding.inflate(inflater)
        dbRef = FirebaseDatabase.getInstance().getReference(GROUP)

        surname = binding.surnameField
        name = binding.nameField
        fatherName = binding.middleNameField
        APKS = binding.apksField
        MZOSZ = binding.mzoszField
        MOS = binding.mosField
        PZIR = binding.pzirField
        PTMO = binding.ptmoField
        TSD = binding.tsdField

        studentsList = mutableListOf()
        studSpinner = binding.studentSpinner

        binding.addStudentButton.setOnClickListener {
            val surnameText = surname.text.toString().trim()
            val nameText = name.text.toString().trim()
            val fatherNameText = fatherName.text.toString().trim()
            val apksText = APKS.text.toString().trim()
            val mzozText = MZOSZ.text.toString().trim()
            val mosText = MOS.text.toString().trim()
            val pzirText = PZIR.text.toString().trim()
            val ptmoText = PTMO.text.toString().trim()
            val tsdText = TSD.text.toString().trim()

            if (surnameText.isNotEmpty() && nameText.isNotEmpty() && fatherNameText.isNotEmpty() &&
                apksText.isNotEmpty() && mzozText.isNotEmpty() && mosText.isNotEmpty() &&
                pzirText.isNotEmpty() && ptmoText.isNotEmpty() && tsdText.isNotEmpty()) {
                val apks = apksText.toInt()
                val mzoz = mzozText.toInt()
                val mos = mosText.toInt()
                val pzir = pzirText.toInt()
                val ptmo = ptmoText.toInt()
                val tsd = tsdText.toInt()

                val student = Student(
                    surname = surnameText,
                    name = nameText,
                    fatherName = fatherNameText,
                    APKS = apks,
                    MZOSZ = mzoz,
                    MOS = mos,
                    PZIR = pzir,
                    PTMO = ptmo,
                    TSD = tsd,
                    rating = (apks + mzoz + mzoz + pzir + ptmo + tsd) / 6.0
                )
                dbRef.push().setValue(student)
                Toast.makeText(requireContext(), "Student added successfully", Toast.LENGTH_SHORT).show()
                surname.setText("")
                name.setText("")
                fatherName.setText("")
                APKS.setText("")
                MZOSZ.setText("")
                MOS.setText("")
                PZIR.setText("")
                PTMO.setText("")
                TSD.setText("")
            } else {
                Toast.makeText(context, "Enter all fields", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = AdminFragment()
    }
}

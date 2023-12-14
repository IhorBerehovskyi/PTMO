package com.myproj.studhelp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.myproj.studhelp.databinding.FragmentAdminRegBinding
import com.myproj.studhelp.databinding.FragmentMapBinding

class AdminRegFragment : Fragment() {

    private lateinit var binding: FragmentAdminRegBinding

    private lateinit var emailLabel: EditText
    private lateinit var passwordLabel: EditText
    private lateinit var confirmPasswordLabel: EditText
    private lateinit var dbRef: DatabaseReference
    private val GROUP = "Admins"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAdminRegBinding.inflate(inflater)
        emailLabel = binding.editTextTextEmailAddress
        passwordLabel = binding.editTextTextPassword
        confirmPasswordLabel = binding.editTextTextConfirmPassword
        dbRef = FirebaseDatabase.getInstance().getReference(GROUP)

        binding.signInLabel.setOnClickListener {
            goToLoginFragment()
        }

        binding.registerButton.setOnClickListener{
            if(!emailLabel.text.toString().endsWith("@lpnu.ua")){
                Toast.makeText(requireContext(), "Only polytechnic email is allowed", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (emailLabel.text.toString().length < 9){
                Toast.makeText(requireContext(), "Too short email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (Regex("\"[^A-Za-z0-9.@_]\"").containsMatchIn(emailLabel.text.toString()) ){
                Toast.makeText(requireContext(), "Inadmissible symbol", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (passwordLabel.text.toString() != confirmPasswordLabel.text.toString()){
                Toast.makeText(requireContext(), "Passwords must be the same", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val admin = Admin(
                email = emailLabel.text.toString(),
                password = passwordLabel.text.toString()
            )
            dbRef.push().setValue(admin)
            goToLoginFragment()
            Toast.makeText(requireContext(), "Registration successful", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    private fun goToLoginFragment() {
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.framePlaceholder, AdminLogFragment.newInstance())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AdminRegFragment()
    }
}
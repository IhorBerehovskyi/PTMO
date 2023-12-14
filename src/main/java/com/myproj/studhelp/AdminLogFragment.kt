package com.myproj.studhelp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.myproj.studhelp.databinding.FragmentAdminLogBinding
import com.myproj.studhelp.databinding.FragmentAdminRegBinding

class AdminLogFragment : Fragment() {

    private lateinit var binding: FragmentAdminLogBinding

    private lateinit var emailLabel: EditText
    private lateinit var passwordLabel: EditText
    private lateinit var dbRef: DatabaseReference
    private val GROUP = "Admins"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAdminLogBinding.inflate(inflater)
        emailLabel = binding.loginEmailAddress
        passwordLabel = binding.loginPassword

        dbRef = FirebaseDatabase.getInstance().getReference(GROUP)

        binding.signUpLabel.setOnClickListener {
            goToRegisterFragment()
        }

        return binding.root
    }

    private fun goToRegisterFragment() {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AdminLogFragment()
    }
}
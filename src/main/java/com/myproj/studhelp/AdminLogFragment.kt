package com.myproj.studhelp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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

        binding.loginButton.setOnClickListener {
            val email = emailLabel.text.toString()
            val password = passwordLabel.text.toString()

            dbRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {

                        val userMap = dataSnapshot.children.first().value as Map<String, Any>

                        if (userMap["password"] == password) {
                            val fragmentManager = requireActivity().supportFragmentManager
                            val transaction = fragmentManager.beginTransaction()

                            transaction.replace(R.id.framePlaceholder, AdminFragment.newInstance())
                            transaction.addToBackStack(null)
                            transaction.commit()
                        } else {
                            Toast.makeText(context, "Wrong password", Toast.LENGTH_SHORT).show()
                        }
                    } else {

                        Toast.makeText(context, "Користувача з такою поштою не знайдено", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Обробка помилок бази даних
                    Toast.makeText(context, "Помилка при читанні з бази даних", Toast.LENGTH_SHORT).show()
                }
            })
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AdminLogFragment()
    }
}
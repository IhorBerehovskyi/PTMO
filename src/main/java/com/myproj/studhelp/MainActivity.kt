package com.myproj.studhelp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.myproj.studhelp.databinding.ActivityMainBinding
import com.myproj.studhelp.R


class MainActivity : AppCompatActivity() {

    private lateinit var bindingClass : ActivityMainBinding
    private var currentFragment: Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        supportFragmentManager.beginTransaction().replace(R.id.framePlaceholder,
            MarksFragment.newInstance()).commit()

        currentFragment = supportFragmentManager.findFragmentById(R.id.framePlaceholder)


        //////////////////////////////////////////////

        bindingClass.marksButton.setOnClickListener {
            if (currentFragment !is MarksFragment) {
                //Log.e("********", "8888888")
                replaceFragmentIfNeeded(MarksFragment.newInstance())
            }
        }

        bindingClass.todoButton.setOnClickListener {
            if (currentFragment !is ToDoFragment) {
                replaceFragmentIfNeeded(ToDoFragment.newInstance())
            }
        }

        bindingClass.mapButton.setOnClickListener {
            if (currentFragment !is MapFragment) {
                replaceFragmentIfNeeded(MapFragment.newInstance())
            }
        }

        bindingClass.adminButton.setOnClickListener {
            if (currentFragment !is AdminFragment) {
                replaceFragmentIfNeeded(AdminFragment.newInstance())
            }
        }
    }

    private fun replaceFragmentIfNeeded(targetFragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.framePlaceholder,
            targetFragment).commit()
            currentFragment = targetFragment
    }


}
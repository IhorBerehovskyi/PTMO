package com.myproj.studhelp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.myproj.studhelp.databinding.ActivityMainBinding

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

        bindingClass.marksButton.setOnClickListener{
            if (currentFragment !is MarksFragment) {
                supportFragmentManager.beginTransaction().replace(
                    R.id.framePlaceholder,
                    MarksFragment.newInstance()
                ).commit()
                currentFragment = MarksFragment.newInstance()
            }
        }

        bindingClass.todoButton.setOnClickListener{
            if (currentFragment !is ToDoFragment) {
                supportFragmentManager.beginTransaction().replace(
                    R.id.framePlaceholder,
                    ToDoFragment.newInstance()
                ).commit()
                currentFragment = ToDoFragment.newInstance()
            }
        }

        bindingClass.mapButton.setOnClickListener{
            if (currentFragment !is MapFragment) {
                supportFragmentManager.beginTransaction().replace(
                    R.id.framePlaceholder,
                    MapFragment.newInstance()
                ).commit()
                currentFragment = MapFragment.newInstance()
            }
        }

        bindingClass.adminButton.setOnClickListener {
            if (currentFragment !is AdminFragment) {
                supportFragmentManager.beginTransaction().replace(
                    R.id.framePlaceholder,
                    AdminFragment.newInstance()
                ).commit()
                currentFragment = AdminFragment.newInstance()
            }
        }
    }



}
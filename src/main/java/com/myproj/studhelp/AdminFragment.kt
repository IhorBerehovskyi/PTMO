package com.myproj.studhelp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

class AdminFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val screenHeight = resources.displayMetrics.heightPixels
        val adminLayout = view.findViewById<FrameLayout>(R.id.adminLayout)

        adminLayout.layoutParams.height = screenHeight / 2
        adminLayout.requestLayout()



    }

    companion object {
        @JvmStatic
        fun newInstance() = AdminFragment()
    }
}
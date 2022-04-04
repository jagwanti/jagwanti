package com.locus.weatherapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.locus.weatherapp.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    fun replaceFragments(fragmentClass: Class<*>,data:Bundle?=null) {
        var fragment: Fragment? = null
        try {
            fragment = fragmentClass.newInstance() as Fragment
            fragment.arguments = data
        } catch (e: Exception) {
            e.printStackTrace()
        }
        fragment?.let {
            // Insert the fragment by replacing any existing fragment
            val fragmentManager: FragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(fragmentClass.simpleName).commit()
        }
    }
}
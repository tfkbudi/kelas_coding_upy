package com.sample.kontak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        changeFragment(supportFragmentManager, MainFragment(), R.id.frameLayout)
    }

    private fun changeFragment(fragmentManager: FragmentManager, fragment: Fragment, layout: Int) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(layout, fragment)

        transaction.commit()
    }
}

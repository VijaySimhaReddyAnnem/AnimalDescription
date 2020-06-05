package com.example.animaldemo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.animaldemo.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navcontroller: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navcontroller=Navigation.findNavController(this,R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this,navcontroller)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navcontroller,null)
    }
}

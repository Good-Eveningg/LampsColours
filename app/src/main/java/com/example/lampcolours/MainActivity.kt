package com.example.lampcolours

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toggle = ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        nav_view.setNavigationItemSelectedListener() {
            when (it.itemId) {
                R.id.colorPicker -> {
                    Navigation
                        .findNavController(findViewById(R.id.nav_host_fragment))
                        .navigate(R.id.action_blueToothFragment_to_startFragment)
                }
                R.id.bluetoothChooser -> {
                    Navigation
                        .findNavController(findViewById(R.id.nav_host_fragment))
                        .navigate(R.id.action_startFragment_to_blueToothFragment)
                }

            }
            true
        }


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}


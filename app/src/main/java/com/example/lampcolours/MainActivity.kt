package com.example.lampcolours

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.lampcolours.screens.BlueToothFragment.BlueToothFragment
import com.example.lampcolours.screens.BlueToothFragment.REQUEST_ENABLE_BT
import com.example.lampcolours.screens.StartFragment.StartFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    var lastclicked = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permissionsChecker()
        toggle = ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        nav_view.setNavigationItemSelectedListener() {
            when (it.itemId) {
                R.id.colorPicker -> {
                    if (lastclicked != it.itemId) {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.nav_host_fragment, StartFragment()).commit()
                        lastclicked = it.itemId
                    }
                    drawer_layout.close()
                }
                R.id.bluetoothChooser -> {
                    if (lastclicked != it.itemId) {
                        supportFragmentManager.beginTransaction().replace(
                            R.id.nav_host_fragment,
                            BlueToothFragment(), "BT"
                        ).commit()
                        lastclicked = it.itemId

                    }
                    drawer_layout.close()
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

    private fun permissionsChecker() {
        if (Build.VERSION.SDK_INT >= 30) {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    android.Manifest.permission.BLUETOOTH_CONNECT
                ) == PackageManager.PERMISSION_GRANTED
            ) {

            } else {
                requestPermissions(
                    arrayOf(android.Manifest.permission.BLUETOOTH_CONNECT),
                    REQUEST_ENABLE_BT
                )
            }
        }
    }

}


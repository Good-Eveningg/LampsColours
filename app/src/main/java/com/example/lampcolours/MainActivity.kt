package com.example.lampcolours

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import com.example.lampcolours.screens.BlueToothFragment.BlueToothFragment
import com.example.lampcolours.screens.BlueToothFragment.REQUEST_ENABLE_BT
import com.example.lampcolours.screens.StartFragment.StartFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    var lastclicked = 0
    lateinit var currentMac: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permissionsChecker()
        getSharedPref()
        firstScreenMode()
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

    fun navigate() {
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, StartFragment())
            .commit()
    }

    private fun getSharedPref(): String? {
        val sharedPref = getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE)
        currentMac = sharedPref?.getString(CURRENT_MAC, null).toString()
        return sharedPref?.getString(CURRENT_MAC, null)
    }

    private fun firstScreenMode(){
        if (currentMac == "null"){
            supportFragmentManager.beginTransaction().replace(
                R.id.nav_host_fragment,
                BlueToothFragment(), "BT"
            ).commit()
            lastclicked = 2
        } else{
            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, StartFragment())
                .commit()
            lastclicked = 1
        }
    }
}


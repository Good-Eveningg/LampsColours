package com.example.lampcolours.screens

import android.bluetooth.BluetoothManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.lampcolours.R
import com.example.lampcolours.REQUEST_ENABLE_BT
import com.example.lampcolours.screens.blueToothScreen.BlueToothFragment
import com.example.lampcolours.screens.startScreen.StartFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle
    private var lastclicked = 0
    private var currentMac: String? = null
    private val btManager by lazy {
        ContextCompat.getSystemService(
            this,
            BluetoothManager::class.java
        )
    }
    private val btAdapter by lazy { btManager?.adapter }
    private val mainActivityViewModel by viewModel<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isPermissionGaranted()
        currentMac = mainActivityViewModel.getMac().toString()
        if (isPermissionGaranted()) {
            mainActivityViewModel.connectDevice(currentMac, btAdapter)
        }

        mainActivityViewModel.myCurrentMacLiveData.observe(this) {
            lastclicked = if (currentMac == "null") {
                supportFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment,
                    BlueToothFragment(), "BT"
                ).commit()
                2
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, StartFragment())
                    .commit()
                1
            }
        }

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

    private fun isPermissionGaranted(): Boolean {
        if (Build.VERSION.SDK_INT >= 30) {
            if (!mainActivityViewModel.checkPermission()) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.BLUETOOTH_CONNECT),
                    REQUEST_ENABLE_BT
                )
            }
        }
        return true

    }

    fun setLastClicked() {
        lastclicked = 1
    }

//    private fun firstScreenMode(){
//        lastclicked = if (currentMac == "null"){
//            supportFragmentManager.beginTransaction().replace(
//                R.id.nav_host_fragment,
//                BlueToothFragment(), "BT"
//            ).commit()
//            2
//        } else{
//            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, StartFragment())
//                .commit()
//            1
//        }
//    }

}


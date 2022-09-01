package com.example.lampcolours.screens

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.lampcolours.APP
import com.example.lampcolours.R
import com.example.lampcolours.REQUEST_ENABLE_BT
import com.example.lampcolours.broadcastreceivers.MyBroadcastReceiver
import com.example.lampcolours.screens.blueToothScreen.BlueToothFragment
import com.example.lampcolours.screens.colorsPalette.colorPalette.ColorPalette
import com.example.lampcolours.screens.startScreen.StartFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle
    private var currentMac: String? = null
    private val btManager by lazy {
        ContextCompat.getSystemService(
            this,
            BluetoothManager::class.java
        )
    }
    private val myBroadcast: BroadcastReceiver = MyBroadcastReceiver()
    private var myFilter = IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED)
    private var myFilter2 = IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED)
    private val btAdapter by lazy { btManager?.adapter }
    private val mainActivityViewModel by viewModel<MainActivityViewModel>()
    private val navHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment }
    val navController by lazy { navHostFragment.navController }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isPermissionGaranted()
        APP = this
        registerReceiver(myBroadcast, myFilter)
        registerReceiver(myBroadcast, myFilter2)
        currentMac = mainActivityViewModel.getMac().toString()
        connectToDevice()

        mainActivityViewModel.myCurrentMacLiveData.observe(this) {
            if (currentMac == "null") {
                navController.navigate(R.id.action_startFragment_to_blueToothFragment)
                mainActivityViewModel.setLastClicked(1)
            } else {
                mainActivityViewModel.setLastClicked(0)
            }
        }

        toggle = ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.colorPicker -> {
                    if (mainActivityViewModel.getLastClicked() != 0) {
                        if (mainActivityViewModel.getLastClicked() == 1) {
                            navController.navigate(R.id.action_blueToothFragment_to_startFragment)
                            // clicked = 0
                        } else if (mainActivityViewModel.getLastClicked() == 2) {
                            navController.navigate(R.id.action_colorPalette2_to_startFragment)
                            // clicked = 0
                        }
                    }
                    drawer_layout.close()
                }
                R.id.bluetoothChooser -> {
                    if (mainActivityViewModel.getLastClicked() != 1) {
                        if (mainActivityViewModel.getLastClicked() == 0) {
                            navController.navigate(R.id.action_startFragment_to_blueToothFragment)
                            //clicked = 1
                        } else if (mainActivityViewModel.getLastClicked() == 2) {
                            navController.navigate(R.id.action_colorPalette2_to_blueToothFragment)
                            // clicked = 1
                        }
                    }
                    drawer_layout.close()
                }
                R.id.colorPalette -> {
                    if (mainActivityViewModel.getLastClicked() != 2) {
                        if (mainActivityViewModel.getLastClicked() == 1) {
                            navController.navigate(R.id.action_blueToothFragment_to_colorPalette2)
                            //clicked = 2
                        } else if (mainActivityViewModel.getLastClicked() == 0) {
                            navController.navigate(R.id.action_startFragment_to_colorPalette2)
                            //clicked = 2
                        }
                    }
                    drawer_layout.close()
                }
            }
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myBroadcast)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(Build.VERSION_CODES.S)
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

    @RequiresApi(Build.VERSION_CODES.S)
    fun connectToDevice() {
        if (isPermissionGaranted()) {
            mainActivityViewModel.connectDevice(currentMac, btAdapter)
        }
    }

    fun sendMessageToArduino(red: Int, green: Int, blue: Int, brightness: Int, onOffStatus: Int) {
        val message = "$red $green $blue $brightness $onOffStatus"
        if (btAdapter?.isEnabled == true) {
            if(mainActivityViewModel.getSocketState() == true){
            mainActivityViewModel.sendMessageToArduino(message)
            mainActivityViewModel.saveRedColorValueRGB(red)
            mainActivityViewModel.saveGreenColorValueRGB(green)
            mainActivityViewModel.saveBlueColorValueRGB(blue)
            mainActivityViewModel.saveBrightnessValue(brightness)
            mainActivityViewModel.saveSwitchOnOffStatus(1)}
            else{Toast.makeText(
                applicationContext,
                "Устройство не подключено",
                Toast.LENGTH_LONG
            ).show()
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Включите BT",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun setLastClicked(lastClicked: Int) {
        mainActivityViewModel.setLastClicked(lastClicked)
    }

    fun closeConnection() {
        mainActivityViewModel.closeConnection()
    }

    fun setSocketState(state : Boolean){
        mainActivityViewModel.setSocketState(state)
    }

}


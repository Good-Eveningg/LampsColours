package com.example.lampcolours.broadcastreceivers

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.lampcolours.APP
import com.example.lampcolours.screens.startScreen.StartFragment

class MyBroadcastReceiver : BroadcastReceiver(
) {


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onReceive(context: Context?, intent: Intent?) {

        val action = intent?.action
        if (action != null && action == BluetoothDevice.ACTION_ACL_DISCONNECTED) {
            Toast.makeText(context, "Устройство отключено", Toast.LENGTH_LONG).show()
            APP.closeConnection()
            APP.setSocketState(false)
            APP.connectToDevice()
        } else if (
            action != null && action == BluetoothDevice.ACTION_ACL_CONNECTED
        ) {
            APP.setSocketState(true)
            Toast.makeText(context, "Устройство подключено", Toast.LENGTH_LONG).show()
        }
    }
}
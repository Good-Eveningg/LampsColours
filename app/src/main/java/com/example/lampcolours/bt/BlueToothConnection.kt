package com.example.lampcolours.bt

import android.bluetooth.BluetoothAdapter
import com.example.lampcolours.adapters.BluetToothAdapter

class BlueToothConnection(private val adapter: BluetoothAdapter) {
    lateinit var connectionThread: ConnectThread
    fun connect(mac: String) {
        if (adapter.isEnabled && mac.isNotEmpty()) {
            val device = adapter.getRemoteDevice(mac)
            device.let {
                connectionThread = ConnectThread(it)
                connectionThread.start()
            }
        }
    }
    fun sendMessage (message: String){
        connectionThread.recieveThread.sendMessage(message.toByteArray())
    }
}
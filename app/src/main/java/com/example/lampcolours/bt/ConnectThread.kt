package com.example.lampcolours.bt

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import android.widget.Toast
import java.io.IOException
import java.util.*

class ConnectThread(private val device: BluetoothDevice) : Thread() {
    private val uuid = "00001101-0000-1000-8000-00805F9B34FB"
    private var mySocket: BluetoothSocket? = null
    lateinit var recieveThread: RecieveThread


    init {
        try {
            mySocket = device.createRfcommSocketToServiceRecord(UUID.fromString(uuid))
        } catch (i: IOException) {

        }
    }

    override fun run() {
        try {
            Log.d("My device", "Connecting")
            mySocket?.connect()
            recieveThread = RecieveThread(mySocket!!)
            recieveThread.start()
        } catch (i: IOException) {
            Log.d("My device", "Failed")
            closeConnection()
        }
    }

    fun closeConnection() {
        try {
            mySocket?.close()
        } catch (i: IOException) {

        }
    }
}
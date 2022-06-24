package com.example.lampcolours.data.blueTooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.util.*

class ConnectedDeviceCommunicationImpl {
    private val uuid = "00001101-0000-1000-8000-00805F9B34FB"
    private var mySocket: BluetoothSocket? = null

    fun createConnectionSocket(mac: String, btAdapter: BluetoothAdapter): BluetoothSocket? {
        try {
            mySocket = btAdapter.getRemoteDevice(mac)
                .createRfcommSocketToServiceRecord(UUID.fromString(uuid))
        } catch (i: IOException) {
            i.printStackTrace()
        }
        return mySocket
    }

    fun connectDevice() {
        try {
            Log.d("My device", "Connecting")
            mySocket?.connect()

        } catch (i: IOException) {
            Log.d("My device", "Failed")
            closeConnection()
        }
    }

    private fun closeConnection() {
        try {
            mySocket?.close()
        } catch (i: IOException) {
            i.printStackTrace()
        }
    }

}
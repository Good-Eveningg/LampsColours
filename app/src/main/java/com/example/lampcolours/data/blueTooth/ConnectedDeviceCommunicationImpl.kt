package com.example.lampcolours.data.blueTooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.util.*

class ConnectedDeviceCommunicationImpl {
    private val uuid = "00001101-0000-1000-8000-00805F9B34FB"
    var mySocket: BluetoothSocket? = null
    private var socketState: Boolean = false

    @SuppressLint("MissingPermission")
    fun createConnectionSocket(mac: String, btAdapter: BluetoothAdapter): BluetoothSocket? {
        try {
            mySocket = btAdapter.getRemoteDevice(mac)
                .createRfcommSocketToServiceRecord(UUID.fromString(uuid))
        } catch (i: IOException) {
            i.printStackTrace()
        }
        return mySocket
    }

    @SuppressLint("MissingPermission")
    fun connectDevice() {
        try {
            Log.d("My device", "Connecting")
            mySocket?.connect()

        } catch (i: IOException) {
            Log.d("My device", "Failed")
            closeConnection()
        }
    }

    fun closeConnection() {
        try {
            mySocket?.close()
        } catch (i: IOException) {
            i.printStackTrace()
        }
    }

    fun setSocketState(state:Boolean){
        socketState = state
    }

    fun getSocketState() : Boolean{
        return socketState
    }
}
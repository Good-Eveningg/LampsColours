package com.example.lampcolours.bt

import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import kotlin.math.sign

class RecieveThread(val bSocket: BluetoothSocket) : Thread() {
    var inputStream: InputStream? = null
    var outputStream: OutputStream? = null

    init {
        try {
            inputStream = bSocket.inputStream
        } catch (i: IOException) {

        }
        try {
            outputStream = bSocket.outputStream
        } catch (i: IOException) {

        }
    }

    override fun run() {
        val buf = ByteArray(4)

        while (true) {
            try {
                val size = inputStream?.read(buf)
                val message = String(buf, 0, size!!)
                Log.d("My device", "$message")
            } catch (i: IOException) {
                break
            }
        }
    }

    fun sendMessage(byteArray: ByteArray) {
        try {
            outputStream?.write(byteArray)
        } catch (i: IOException) {

        }
    }
}
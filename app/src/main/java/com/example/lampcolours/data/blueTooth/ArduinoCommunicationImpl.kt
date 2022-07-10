package com.example.lampcolours.data.blueTooth

import android.bluetooth.BluetoothSocket
import com.example.lampcolours.models.domain.ArduinoResponse
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class ArduinoCommunicationImpl {


    var inputStream: InputStream? = null
    var outputStream: OutputStream? = null

    fun initSocket(bSocket: BluetoothSocket) {
        try {
            inputStream = bSocket.inputStream
        } catch (i: IOException) {

        }
        try {
            outputStream = bSocket.outputStream
        } catch (i: IOException) {

        }
    }

    fun getArduinoResponse(arduinoResponse: ArduinoResponse) {
        val buf = ByteArray(4)

        while (true) {
            try {
                val size = inputStream?.read(buf)
                val message = String(buf, 0, size!!)
                arduinoResponse.arduinoResponse(message)
            } catch (i: IOException) {
                break
            }
        }
    }

    fun sendMessageToArduino(byteArray: ByteArray) {
        try {
            outputStream?.write(byteArray)
        } catch (i: IOException) {

        }
    }

}
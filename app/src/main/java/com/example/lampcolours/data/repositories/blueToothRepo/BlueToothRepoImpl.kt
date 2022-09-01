package com.example.lampcolours.data.repositories.blueToothRepo

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import com.example.lampcolours.data.blueTooth.ArduinoCommunicationImpl
import com.example.lampcolours.data.blueTooth.ConnectedDeviceCommunicationImpl
import com.example.lampcolours.data.colorPickerDataRepo.ColorRepoImpl
import com.example.lampcolours.data.repositories.sharedPrefRepo.SharedPrefRepoImpl
import com.example.lampcolours.models.domain.ArduinoResponse
import com.example.lampcolours.models.domain.BluetoothItem

class BlueToothRepoImpl(
    private val arduinoCommunicationImpl: ArduinoCommunicationImpl,
    private var connectedDeviceCommunicationImpl: ConnectedDeviceCommunicationImpl,
    private val colorPrefRepoImpl: ColorRepoImpl

) : ArduinoResponse {

    private var arduinoResponseMessage: String = "null"

    fun connectDeviceToArduino(
        mac: String?,
        adapter: BluetoothAdapter
    ): Boolean {
        return if (adapter.isEnabled && mac != "null") {
            if (mac != null) {
                connectedDeviceCommunicationImpl.createConnectionSocket(mac, adapter)
            }
            val bSocket =
                mac?.let { connectedDeviceCommunicationImpl.createConnectionSocket(it, adapter) }
            if (bSocket != null) {
                arduinoCommunicationImpl.initSocket(bSocket)
            }
            connectedDeviceCommunicationImpl.connectDevice()
            arduinoCommunicationImpl.getArduinoResponse(this)
            sendMessageToArduino("0 0 0 0 0")
            colorPrefRepoImpl.saveSwitchOnOffStatus(0)
            true
        } else {
            false
        }
    }
     fun closeConnection(){
         connectedDeviceCommunicationImpl.closeConnection()
     }

    fun getArduinoResponseMessage(): String {
        return arduinoResponseMessage
    }

    fun sendMessageToArduino(message: String) {
        arduinoCommunicationImpl.sendMessageToArduino(message.toByteArray())
    }

    fun setSocketState(state :Boolean){
        connectedDeviceCommunicationImpl.setSocketState(state)
    }

    fun getSocketState(): Boolean? {
        return connectedDeviceCommunicationImpl.getSocketState()
    }

    @SuppressLint("MissingPermission")
    fun getListDevicesToBlueToothConnect(
        btAdapter: BluetoothAdapter
    ): List<BluetoothItem> {
        val tempList = ArrayList<BluetoothItem>()
        if (btAdapter.isEnabled) {
            val pairedDevices: Set<BluetoothDevice>? = btAdapter.bondedDevices
            pairedDevices?.forEach {
                tempList.add(BluetoothItem(it.name, it.address))
            }
        } else {
            tempList.clear()
        }
        return tempList
    }

    override fun arduinoResponse(message: String) {
        arduinoResponseMessage = message
    }


}
package com.example.lampcolours.data.repositories.blueToothRepo

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import com.example.lampcolours.data.blueTooth.ArduinoCommunicationImpl
import com.example.lampcolours.data.blueTooth.ConnectedDeviceCommunicationImpl
import com.example.lampcolours.models.domain.BluetoothItem

class BlueToothRepoImpl(
    private val arduinoCommunicationImpl: ArduinoCommunicationImpl,
    private var connectedDeviceCommunicationImpl: ConnectedDeviceCommunicationImpl
) {

    suspend fun connectDeviceToArduino(
        mac: String?,
        adapter: BluetoothAdapter
    ): Boolean {
        return if (adapter.isEnabled && mac?.isNotEmpty() == true) {
            connectedDeviceCommunicationImpl.createConnectionSocket(mac, adapter)
            val bSocket = connectedDeviceCommunicationImpl.createConnectionSocket(mac, adapter)
            if (bSocket != null) {
                arduinoCommunicationImpl.initSocket(bSocket)
            }
            connectedDeviceCommunicationImpl.connectDevice()
            true
        } else {
            false
        }
    }

    fun connectDeviceToArduino2(
        mac: String,
        adapter: BluetoothAdapter,
    ): Boolean {
        return if (adapter.isEnabled && mac.isNotEmpty()) {
            val device = adapter.getRemoteDevice(mac)
            device.let {
                connectedDeviceCommunicationImpl =
                    ConnectedDeviceCommunicationImpl()
                true
            }
        } else {
            false
        }
    }

    fun sendMessageToArduino(message: String) {
        arduinoCommunicationImpl.sendMessageToArduino(message.toByteArray())
    }

    fun getListDevicesToBlueToothConnect(
        btAdapter: BluetoothAdapter
    ): List<BluetoothItem> {
        val tempList = ArrayList<BluetoothItem>()
        val pairedDevices: Set<BluetoothDevice>? = btAdapter?.bondedDevices
        pairedDevices?.forEach {
            tempList.add(BluetoothItem(it.name, it.address))
        }
        return tempList
    }

}
package com.example.lampcolours.screens.blueToothScreen

import android.bluetooth.BluetoothAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lampcolours.data.repositories.blueToothRepo.BlueToothRepoImpl
import com.example.lampcolours.data.repositories.sharedPrefRepo.SharedPrefRepoImpl
import com.example.lampcolours.models.domain.BluetoothItem
import kotlinx.coroutines.launch

class BlueToothViewModel(
    private val blueToothRepoImpl: BlueToothRepoImpl,
    private val sharedPrefRepoImpl: SharedPrefRepoImpl
) : ViewModel() {

    private val mDevicesListToBTConnect = MutableLiveData<List<BluetoothItem>>()
    val devicesListToBTConnect: LiveData<List<BluetoothItem>> = mDevicesListToBTConnect

    fun connectDevice(
        mac: String?, adapter: BluetoothAdapter?
    ) {
        viewModelScope.launch {
            sharedPrefRepoImpl.saveData(mac)
            if (adapter != null) {
                blueToothRepoImpl.connectDeviceToArduino(mac, adapter)
            }
        }
    }

    fun getDevicesList(btAdapter: BluetoothAdapter) {
        mDevicesListToBTConnect.postValue(
            blueToothRepoImpl.getListDevicesToBlueToothConnect(
                btAdapter
            )
        )
    }

}
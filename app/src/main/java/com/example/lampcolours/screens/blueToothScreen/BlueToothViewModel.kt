package com.example.lampcolours.screens.blueToothScreen

import android.bluetooth.BluetoothAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lampcolours.data.repositories.blueToothRepo.BlueToothRepoImpl
import com.example.lampcolours.data.repositories.sharedPrefRepo.SharedPrefRepoImpl
import com.example.lampcolours.models.domain.BluetoothItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BlueToothViewModel(
    private val blueToothRepoImpl: BlueToothRepoImpl,
    private val sharedPrefRepoImpl: SharedPrefRepoImpl
) : ViewModel() {

    private val mDevicesListToBTConnect = MutableLiveData<List<BluetoothItem>>()
    val devicesListToBTConnect: LiveData<List<BluetoothItem>> = mDevicesListToBTConnect



    fun getDevicesList(btAdapter: BluetoothAdapter) {
        mDevicesListToBTConnect.postValue(
            blueToothRepoImpl.getListDevicesToBlueToothConnect(
                btAdapter
            )
        )
    }

    fun saveMac(mac: String?) {
        sharedPrefRepoImpl.saveData(mac)
    }
}
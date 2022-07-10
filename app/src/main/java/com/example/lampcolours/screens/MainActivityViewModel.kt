package com.example.lampcolours.screens

import android.bluetooth.BluetoothAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lampcolours.data.repositories.blueToothRepo.BlueToothRepoImpl
import com.example.lampcolours.data.repositories.permissionRepo.PermissionRepoImpl
import com.example.lampcolours.data.repositories.sharedPrefRepo.SharedPrefRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val sharedPrefRepoImpl: SharedPrefRepoImpl,
    private val permissionRepoImpl: PermissionRepoImpl,
    private val blueToothRepoImpl: BlueToothRepoImpl
) : ViewModel() {

    private val mMyCurrentMacLiveData = MutableLiveData<String>()
    val myCurrentMacLiveData: LiveData<String> = mMyCurrentMacLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            mMyCurrentMacLiveData.postValue(sharedPrefRepoImpl.getSharedPref())
        }
    }

    fun connectDevice(
        mac: String?, adapter: BluetoothAdapter?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (adapter != null) {
                while (blueToothRepoImpl.getSocketState() != true) {
                    try {
                        blueToothRepoImpl.connectDeviceToArduino(mac, adapter)
                    } catch (i: Exception) {

                    }

                }
            }
        }
    }

    fun checkPermission(): Boolean {
        return permissionRepoImpl.permissionsChecker()
    }

    fun getMac(): String? {
        return sharedPrefRepoImpl.getSharedPref()
    }


}
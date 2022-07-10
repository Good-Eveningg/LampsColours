package com.example.lampcolours.screens.startScreen

import android.bluetooth.BluetoothAdapter
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lampcolours.data.colorPickerDataRepo.ColorRepoImpl
import com.example.lampcolours.data.repositories.blueToothRepo.BlueToothRepoImpl
import com.example.lampcolours.data.repositories.sharedPrefRepo.SharedPrefRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StartViewModel(
    private val blueToothRepoImpl: BlueToothRepoImpl,
    private val colorRepoImpl: ColorRepoImpl,
    private val sharedPrefRepoImpl: SharedPrefRepoImpl
) : ViewModel() {

    private var arduinoMessage: Boolean = false

    fun sendMessageToArduino(message: String, adapter: BluetoothAdapter) {
        if (getSocketState() == true) {
            blueToothRepoImpl.sendMessageToArduino(message)
        } else {
            try {
                blueToothRepoImpl.connectDeviceToArduino(getMac(), adapter)
                blueToothRepoImpl.sendMessageToArduino(message)
            } catch (i: Exception) {
                Log.d("My device", "Failed to send message")
            }
        }
    }

    private fun getMac(): String? {
        return sharedPrefRepoImpl.getSharedPref()
    }

    fun getSocketState(): Boolean? {
        return blueToothRepoImpl.getSocketState()
    }




    fun saveRedColorValueRGB(redColorValue: Int?) {
        colorRepoImpl.saveRedColorValueRGB(redColorValue)
    }

    fun saveGreenColorValueRGB(greenColorValue: Int?) {
        colorRepoImpl.saveGreenColorValueRGB(greenColorValue)
    }

    fun saveBlueColorValueRGB(blueColorValue: Int?) {
        colorRepoImpl.saveBlueColorValueRGB(blueColorValue)
    }

    fun saveBrightnessValue(brightnessValue: Int?) {
        colorRepoImpl.saveBrightnessValue(brightnessValue)
    }

    fun getRedColor(): Int? {
        return colorRepoImpl.getRedColorValueRGB()
    }

    fun getGreenColor(): Int? {
        return colorRepoImpl.getGreenColorValueRGB()
    }

    fun getBlueColor(): Int? {
        return colorRepoImpl.getBlueColorValueRGB()
    }

    fun getBrightness(): Int? {
        return colorRepoImpl.getBrightnessValue()
    }

}
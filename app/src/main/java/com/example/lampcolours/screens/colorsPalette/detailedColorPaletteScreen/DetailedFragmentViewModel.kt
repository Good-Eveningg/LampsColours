package com.example.lampcolours.screens.colorsPalette.detailedColorPaletteScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lampcolours.REPOSITORY
import com.example.lampcolours.data.colorPickerDataRepo.ColorRepoImpl
import com.example.lampcolours.models.colorPalette.ColorPaletteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailedFragmentViewModel : ViewModel() {
    private lateinit var currentColorNote: ColorPaletteModel
    private var red = 155
    private var green = 155
    private var blue = 155
    private var brightness = 69


    fun getCurrentColor(colorPaletteModel: ColorPaletteModel) {
        currentColorNote = colorPaletteModel
    }

    fun getRed(): Int {
        return currentColorNote.redColor
    }

    fun getBlue(): Int {
        return currentColorNote.blueColor
    }

    fun getGreen(): Int {
        return currentColorNote.greenColor
    }

    fun getBrightness(): Int {
        return currentColorNote.brightnessValue
    }

    fun saveBrightnessValue(brightnessValue: Int){
            brightness = brightnessValue
    }

    fun saveRed(redValue: Int){
        red = redValue
    }

    fun saveGreen(greenValue: Int){
        green = greenValue
    }

    fun saveBlue(blueValue: Int){
        blue = blueValue
    }

    fun updateCurrentColor() {
        currentColorNote.redColor = red
        currentColorNote.greenColor = green
        currentColorNote.blueColor = blue
        currentColorNote.brightnessValue = brightness
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.updateColor(currentColorNote)
        }
    }

    fun deleteCurrentColor() {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.deleteColor(currentColorNote)
        }
    }
}
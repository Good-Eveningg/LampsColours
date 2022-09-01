package com.example.lampcolours.screens.colorsPalette.colorPalette

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lampcolours.REPOSITORY
import com.example.lampcolours.data.colorPickerDataRepo.ColorRepoImpl
import com.example.lampcolours.data.db.colorPaletteDAO.ColorPaletteDAO
import com.example.lampcolours.data.db.colorPaletteDataBase.ColorPaletteDataBase
import com.example.lampcolours.data.repositories.blueToothRepo.BlueToothRepoImpl
import com.example.lampcolours.data.repositories.colorPalette.ColorPaletteRepoRealization
import com.example.lampcolours.models.colorPalette.ColorPaletteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vadiole.colorpicker.ColorModel

class ColorPaletteViewModel
    (
    private val colorRepoImpl: ColorRepoImpl
) : ViewModel() {

    fun getRedColor(): Int? {
        return colorRepoImpl.getRedColorValueRGB()
    }

    fun getGreenColor(): Int? {
        return colorRepoImpl.getGreenColorValueRGB()
    }

    fun getBlueColor(): Int? {
        return colorRepoImpl.getBlueColorValueRGB()
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

    fun initColorPaletteDataBase(context: Context) {
        val daoColorPalette = ColorPaletteDataBase.getInstance(context).getColorPaletteDao()
        REPOSITORY = ColorPaletteRepoRealization(daoColorPalette)
    }

    fun getAllColors(): LiveData<List<ColorPaletteModel>> {
        return REPOSITORY.allPalette
    }

    fun insertColor(colorPaletteModel: ColorPaletteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.insertColor(colorPaletteModel)
        }
    }


}

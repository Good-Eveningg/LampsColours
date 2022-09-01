package com.example.lampcolours.data.repositories.colorPalette

import androidx.lifecycle.LiveData
import com.example.lampcolours.models.colorPalette.ColorPaletteModel
import com.example.lampcolours.screens.colorsPalette.colorPalette.ColorPalette
import vadiole.colorpicker.ColorModel

interface ColorPaletteRepo {
    val allPalette: LiveData<List<ColorPaletteModel>>
    suspend fun insertColor(colorModel: ColorPaletteModel)
    suspend fun deleteColor(colorModel: ColorPaletteModel)
    suspend fun updateColor(colorModel: ColorPaletteModel)

}
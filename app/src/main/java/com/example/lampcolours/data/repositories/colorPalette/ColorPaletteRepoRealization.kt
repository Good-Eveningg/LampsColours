package com.example.lampcolours.data.repositories.colorPalette

import androidx.lifecycle.LiveData
import androidx.room.Dao
import com.example.lampcolours.data.db.colorPaletteDAO.ColorPaletteDAO
import com.example.lampcolours.models.colorPalette.ColorPaletteModel
import com.example.lampcolours.screens.colorsPalette.colorPalette.ColorPalette
import vadiole.colorpicker.ColorModel

class ColorPaletteRepoRealization(private val colorPaletteDao: ColorPaletteDAO):ColorPaletteRepo {


    override val allPalette: LiveData<List<ColorPaletteModel>>
        get() = colorPaletteDao.getAllColors()

    override suspend fun insertColor(colorModel: ColorPaletteModel) {
        colorPaletteDao.insertColor(colorModel)
    }

    override suspend fun deleteColor(colorModel: ColorPaletteModel) {
        colorPaletteDao.deleteColor(colorModel)
    }

    override suspend fun updateColor(colorModel: ColorPaletteModel) {
        colorPaletteDao.updateColor(colorModel)
    }
}
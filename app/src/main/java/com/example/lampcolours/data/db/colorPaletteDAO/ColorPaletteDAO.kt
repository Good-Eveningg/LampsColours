package com.example.lampcolours.data.db.colorPaletteDAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lampcolours.models.colorPalette.ColorPaletteModel
import vadiole.colorpicker.ColorModel

@Dao
interface ColorPaletteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertColor(colorModel: ColorPaletteModel)

    @Delete
    suspend fun deleteColor(colorModel: ColorPaletteModel)

    @Update
    suspend fun updateColor(colorModel: ColorPaletteModel)

    @Query("SELECT * from color_palette")
    fun getAllColors(): LiveData<List<ColorPaletteModel>>
}
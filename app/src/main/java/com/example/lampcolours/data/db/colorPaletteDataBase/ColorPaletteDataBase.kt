package com.example.lampcolours.data.db.colorPaletteDataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lampcolours.data.db.colorPaletteDAO.ColorPaletteDAO
import com.example.lampcolours.models.colorPalette.ColorPaletteModel
import vadiole.colorpicker.ColorModel

@Database(entities = [ColorPaletteModel::class], version = 1)
abstract class ColorPaletteDataBase:RoomDatabase() {
    abstract fun getColorPaletteDao():ColorPaletteDAO

    companion object{
        private var database:ColorPaletteDataBase ?= null

        @Synchronized
        fun getInstance(context : Context):ColorPaletteDataBase{
            return if (database == null){
                database = Room.databaseBuilder(context, ColorPaletteDataBase::class.java, "color_db")
                    .build()
                database as ColorPaletteDataBase
            }else{
                database as ColorPaletteDataBase
            }
        }
    }
}
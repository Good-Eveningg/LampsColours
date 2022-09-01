package com.example.lampcolours.models.colorPalette

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "color_palette")
class ColorPaletteModel(
    @PrimaryKey(autoGenerate = true)
    var dbId: Int = 0,

    @ColumnInfo
    var redColor: Int = 255,

    @ColumnInfo
    var greenColor: Int = 255,

    @ColumnInfo
    var blueColor: Int = 255,

    @ColumnInfo
    var brightnessValue: Int = 100,

    @ColumnInfo
    var lampSwitchOnStatus: Int = 1
) : Serializable
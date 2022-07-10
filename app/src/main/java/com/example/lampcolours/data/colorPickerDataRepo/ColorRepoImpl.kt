package com.example.lampcolours.data.colorPickerDataRepo

import android.content.Context
import android.content.SharedPreferences
import com.example.lampcolours.*

class ColorRepoImpl(private val context: Context) {

    fun saveRedColorValueRGB(redColorValue: Int?): SharedPreferences {
        val redSharedPref =
            context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE)
        val editor = redSharedPref.edit()
        if (redColorValue != null) {
            editor.putInt(RED_KEY, redColorValue)
        }
        editor.apply()
        return redSharedPref
    }

    fun saveGreenColorValueRGB(greenColorValue: Int?): SharedPreferences {
        val greenSharedPref =
            context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE)
        val editor = greenSharedPref.edit()
        if (greenColorValue != null) {
            editor.putInt(GREEN_KEY, greenColorValue)
        }
        editor.apply()
        return greenSharedPref
    }

    fun saveBlueColorValueRGB(blueColorValue: Int?): SharedPreferences {
        val blueSharedPref =
            context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE)
        val editor = blueSharedPref.edit()
        if (blueColorValue != null) {
            editor.putInt(BLUE_KEY, blueColorValue)
        }
        editor.apply()
        return blueSharedPref
    }

    fun saveBrightnessValue(brightnessValue: Int?): SharedPreferences {
        val brightnessSharedPref =
            context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE)
        val editor = brightnessSharedPref.edit()
        if (brightnessValue != null) {
            editor.putInt(BRIGHTNESS_KEY, brightnessValue)
        }
        editor.apply()
        return brightnessSharedPref
    }

    fun getRedColorValueRGB(): Int? {
        val redSharedPref =
            context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE)
        return redSharedPref?.getInt(RED_KEY, DEFAULT_COLOR_VALUE)
    }

    fun getGreenColorValueRGB(): Int? {
        val greenSharedPref =
            context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE)
        return greenSharedPref?.getInt(GREEN_KEY, DEFAULT_COLOR_VALUE)
    }

    fun getBlueColorValueRGB(): Int? {
        val blueSharedPref =
            context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE)
        return blueSharedPref?.getInt(BLUE_KEY, DEFAULT_COLOR_VALUE)
    }

    fun getBrightnessValue(): Int? {
        val brightnessSharedPref =
            context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE)
        return brightnessSharedPref?.getInt(BRIGHTNESS_KEY, DEFAULT_BRIGHTNESS_VALUE)
    }


}
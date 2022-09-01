package com.example.lampcolours

import com.example.lampcolours.data.repositories.colorPalette.ColorPaletteRepo
import com.example.lampcolours.screens.MainActivity

const val SHARED_PREF_FILE_NAME = "SharedPrefs"
const val COLOR_ID = "color"
const val CURRENT_MAC = "mac"
const val REQUEST_ENABLE_BT = 15
const val RED_KEY = "red"
const val GREEN_KEY = "green"
const val BLUE_KEY = "blue"
const val BRIGHTNESS_KEY = "brightness"
const val SWITCH_STATUS = "switcher"
const val DEFAULT_COLOR_VALUE = 255
const val DEFAULT_BRIGHTNESS_VALUE = 30
const val DEFAULT_SWITCH = 0
lateinit var REPOSITORY : ColorPaletteRepo
lateinit var APP : MainActivity

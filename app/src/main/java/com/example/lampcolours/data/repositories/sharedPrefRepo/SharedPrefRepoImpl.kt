package com.example.lampcolours.data.repositories.sharedPrefRepo

import android.content.Context
import android.content.SharedPreferences
import com.example.lampcolours.CURRENT_MAC
import com.example.lampcolours.SHARED_PREF_FILE_NAME

class SharedPrefRepoImpl(
    private val context: Context
) {

    fun saveData(mac: String?): SharedPreferences {
        val sharedPref =
            context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(CURRENT_MAC, mac)
        editor.apply()
        return sharedPref
    }

    fun getSharedPref(): String? {
        val sharedPref = context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE)
        return sharedPref?.getString(CURRENT_MAC, null).toString()
    }



}
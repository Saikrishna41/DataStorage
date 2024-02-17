package com.techfortyone.datastorage.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferenceManager @Inject constructor (@ApplicationContext private val context: Context) {
    fun savedPreferences(key : String, value : Int)  {
        val pref = context.getSharedPreferences("sharedPrefData", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getPreference(key : String) : Int  {
        val pref = context.getSharedPreferences("sharedPrefData", Context.MODE_PRIVATE)
        return pref.getInt(key, 0)
    }
}
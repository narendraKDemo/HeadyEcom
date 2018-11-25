package com.techguys.headyecomapp.utils

import android.content.SharedPreferences

fun savedInDB(sharedPreferences: SharedPreferences){
    sharedPreferences.edit().putBoolean("SAVED_DB", true).apply()
}


fun isSavedInDB(sharedPreferences: SharedPreferences):Boolean{
   return sharedPreferences.getBoolean("SAVED_DB", false)
}
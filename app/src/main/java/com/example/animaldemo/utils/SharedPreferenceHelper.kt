package com.example.animaldemo.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager


@Suppress("DEPRECATION")
class SharedPreferenceHelper(context: Context) {

    private val PREF_API_KEY="Api Key"
    private val prefs=PreferenceManager.getDefaultSharedPreferences(context.applicationContext)


    fun saveApiKey(key:String)= prefs.edit().putString(PREF_API_KEY,key)
    fun getApiKey()=prefs.getString(PREF_API_KEY,null)





}
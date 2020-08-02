package com.akil.ad340

import android.content.Context

enum class TempDisplaySetting{
    Fahrenheit, Celsius
}

// Taking in a context parameter to the class in constructor
// we need a context to get the reference to the shared preferences

class TempDisplaySettingManager (context: Context){
    // Creating/Opening a file to a disk and making it private to the app
    // This would allow us to store small data to the disk like user settings
    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    // We are going to update our preferences with the setting value from the enum class we made
    fun updateSetting(setting: TempDisplaySetting){
        preferences.edit().putString("key_temp_display", setting.name).apply()
    }

    fun getTempDisplaySetting(): TempDisplaySetting{
        val settingValue = preferences.getString("key_temp_display", TempDisplaySetting.Fahrenheit.name) ?: TempDisplaySetting.Fahrenheit.name
        return TempDisplaySetting.valueOf(settingValue)
    }
}
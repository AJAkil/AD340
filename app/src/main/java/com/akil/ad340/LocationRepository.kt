package com.akil.ad340

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

sealed class Location{
    data class Zipcode(val zipcode: String): Location()
}

// a private property for this file.
private const val KEY_ZIPCODE = "key_zipcode"

class LocationRepository(context: Context) {
    private val preferences = context.getSharedPreferences("settings",Context.MODE_PRIVATE)

    private val _savedLocation : MutableLiveData<Location> = MutableLiveData()
    // So that the external party can observe the change when we do to the private reference
    val savedLocation: LiveData<Location> = _savedLocation

    // just like a constructor body, when an instance is made, it will look for the zipcode that
    // was saved on the shared preferences
    init {
        preferences.registerOnSharedPreferenceChangeListener{ sharedPreferences, key ->
            if (key != KEY_ZIPCODE) return@registerOnSharedPreferenceChangeListener
            broadcastSavedZipcode()
        }

        // we need to notify the current observer with the current zipcode values
        broadcastSavedZipcode()

    }

    // This method will save the location to the shared preferences when it will be called
    fun saveLocation(location: Location){
        when (location){
            is Location.Zipcode -> preferences.edit().putString(KEY_ZIPCODE, location.zipcode).apply()
        }
    }

    private fun broadcastSavedZipcode(){
        val zipcode = preferences.getString(KEY_ZIPCODE, "")
        if (zipcode != null && zipcode.isNotBlank()){
            // we update the observer variables with the correct value
            _savedLocation.value = Location.Zipcode(zipcode)
        }
    }

}
package com.akil.ad340

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class ForecastRepository {

    //this will let us update that observable valueHolder internally to that class
    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()
    // Public Live data so that activity can get access to it
    val weeklyForecast : LiveData<List<DailyForecast>> = _weeklyForecast

    // A method for loading data so that we can pass it to the activity
    fun loadForecast(zipcode: String){
        // Randomly Loading list of seven values
        val randomValues = List(7) { Random.nextFloat().rem(100) * 100 }
        // A list variable for making the data class objects
        val forecastItems = randomValues.map {temp ->
            DailyForecast(temp,"Partly Chilly")
        }

        // the private variable is updated with the list, and the public variable will also be updated
        _weeklyForecast.setValue(forecastItems)
    }
}
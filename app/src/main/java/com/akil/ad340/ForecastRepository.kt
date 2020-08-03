package com.akil.ad340

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class ForecastRepository {

    //this will let us update that observable valueHolder internally to that class
    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()
    // Public Live data so that activity can get access to it
    val weeklyForecast : LiveData<List<DailyForecast>> = _weeklyForecast

    private val _currentForecast = MutableLiveData<DailyForecast>()
    val currentForecast : LiveData<DailyForecast> = _currentForecast


    // A method for loading data so that we can pass it to the activity
    fun loadWeeklyForecast(zipcode: String){
        // Randomly Loading list of seven values
        val randomValues = List(10) { Random.nextFloat().rem(100) * 100 }
        // A list variable for making the data class objects
        val forecastItems = randomValues.map {temp ->
            DailyForecast(temp,getTempDescription(temp))
        }

        // the private variable is updated with the list, and the public variable will also be updated
        _weeklyForecast.value = forecastItems
    }

    // A method for loading data to the current forecast fragment
    fun loadCurrentForecast(zipcode: String){

    }

    private fun getTempDescription(temp: Float): String{
        return when (temp){
            in Float.MIN_VALUE.rangeTo(0f) -> "Anything bellow zero doesn't make sense!"
            in 0f.rangeTo(32f) -> "Way too Cold!"
            in 32f.rangeTo(55f) -> "Colder than I would prefer"
            in 55f.rangeTo(65f) -> "Getting better"
            in 65f.rangeTo(80f) -> "That's the sweet spot"
            in 80f.rangeTo(90f) -> "Getting a little warm"
            in 90f.rangeTo(100f) -> "Where's the A/C?"
            in 100f.rangeTo(Float.MAX_VALUE) -> "What is this, Arizona"
            else -> "Does not compute"
        }
    }
}
package com.akil.ad340

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akil.ad340.api.CurrentWeather
import com.akil.ad340.api.OpenWeatherMapService
import com.akil.ad340.api.createOpenWeatherService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class ForecastRepository {

    //this will let us update that observable valueHolder internally to that class
    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()
    // Public Live data so that activity can get access to it
    val weeklyForecast : LiveData<List<DailyForecast>> = _weeklyForecast

    private val _currentWeather = MutableLiveData<CurrentWeather>()
    val currentWeather : LiveData<CurrentWeather> = _currentWeather


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
        print(zipcode)
        val call = createOpenWeatherService().currentWeather(zipcode,BuildConfig.OPEN_WEATHER_MAP_API_KEY,"imperial")
        call.enqueue(object : Callback<CurrentWeather>{
            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName,"error fetching weather data", t)
            }

            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>
            ) {
                val weatherResponse = response.body()

                if (weatherResponse != null){
                    _currentWeather.value = weatherResponse
                }
            }

        })

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
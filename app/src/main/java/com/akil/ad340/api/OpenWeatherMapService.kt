package com.akil.ad340.api

import com.akil.ad340.Location
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

fun createOpenWeatherService(): OpenWeatherMapService{
    val retrofit = Retrofit.Builder()
        .baseUrl("http://api.openweathermap.org")
        .build()

    //Create an implementation of our API and we will use that API class to get the weather data
    return retrofit.create(OpenWeatherMapService::class.java)
}

interface OpenWeatherMapService {

    @GET("/data/2.5/weather")
    fun currentWeather(
        @Query("zip") zipcode: String,
        @Query("units") units: String,
        @Query("app") apiKey: String
    ): Call<CurrentWeather>

}
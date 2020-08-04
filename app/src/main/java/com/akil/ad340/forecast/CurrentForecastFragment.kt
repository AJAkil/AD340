package com.akil.ad340.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.akil.ad340.*
import com.akil.ad340.api.CurrentWeather
import com.akil.ad340.api.DailyForecast
import com.akil.ad340.details.ForecastDetailsFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CurrentForecastFragment : Fragment() {

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    private val forecastRepository = ForecastRepository()
    private lateinit var locationRepository: LocationRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Setting up the setting manager reference to pass it to the adapter
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        // getting the zipcode from the bundle that we passed to.
        val zipcode = arguments?.getString(KEY_ZIPCODE) ?: ""


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_forecast, container, false)
        val locationName : TextView = view.findViewById(R.id.locationName)
        val tempText: TextView = view.findViewById(R.id.tempText)
        val forecastIcon: ImageView = view.findViewById(R.id.currentForecastIcon)

        // Getting reference to the floating action button
        val locationEntryButton = view.findViewById<FloatingActionButton>(R.id.locationEntryFragmentButton)

        // Adding a click listener to the button
        locationEntryButton.setOnClickListener {
            showLocationEntry()
        }


        val currentWeatherObserver =  Observer<CurrentWeather>{ weather->
            locationName.text = weather.name
            tempText.text = formatTempForDisplay(weather.forecast.temp, tempDisplaySettingManager.getTempDisplaySetting())

            val iconID = weather.weather[0].icon
            forecastIcon.load("http://openweathermap.org/img/wn/${iconID}@2x.png")
        }
        /*we observe the weeklyForecast variable by the weeklyForecastObserver
        A lifecycle owner, the main activity is passed here. Observer is also passed here
        Any time live data changes in the repository due to some reasons, the observer is updated
        which updates the ListAdapter and since we passed lifecycle observer, all of these changes
        will bound to the lifecycle of the activity*/
        forecastRepository.currentWeather.observe(viewLifecycleOwner,currentWeatherObserver)

        // Getting the zipcode from the sharedPreferences by observing the changes to the location
        locationRepository = LocationRepository(requireContext())
        val savedLocationObserver = Observer<Location>{ savedLocation ->
            when (savedLocation){
                // calling the loadForecast of the forecastRepository so that data can be loaded from the database or API call
                is Location.Zipcode -> forecastRepository.loadCurrentForecast(savedLocation.zipcode)
            }
        }

        locationRepository.savedLocation.observe(viewLifecycleOwner, savedLocationObserver)

        return view
    }

    private fun showLocationEntry(){
        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }


    /*
    This is like static methods like java
     */
    companion object{

        //
        const val KEY_ZIPCODE = "key_zipcode"

        // a factory method for the fragment, that takes in any argument for the fragment to
        // operate correctly
        fun newInstance(zipcode: String): CurrentForecastFragment{
            // We create an instance of a fragment
            val fragment = CurrentForecastFragment()

            // Bundle() is a class to pass around key value pairs around fragment arguments or intents
            val args = Bundle()
            args.putString(KEY_ZIPCODE, zipcode)
            fragment.arguments = args

            return fragment
        }
    }

}
package com.akil.ad340.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akil.ad340.*
import com.akil.ad340.details.ForecastDetailsFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CurrentForecastFragment : Fragment() {

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    private val forecastRepository = ForecastRepository()

    // Interface type reference created
    private lateinit var appNavigator: AppNavigator

    // A lifecycle method, called when fragment is added to the activity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // We want to assign appNavigator the value of context
        appNavigator = context as AppNavigator
    }


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

        // Getting reference to the floating action button
        val locationEntryButton = view.findViewById<FloatingActionButton>(R.id.locationEntryFragmentButton)

        // Adding a click listener to the button
        locationEntryButton.setOnClickListener {
            appNavigator.navigateToLocationEntry()
        }


        // Creating a Recycler View instance
        val forecastList: RecyclerView = view.findViewById(R.id.forecastList)

        // Instantiating the linear layout manager
        forecastList.layoutManager = LinearLayoutManager(requireContext())

        /*
        * So, we are passing a call back here that would be used in the Adaptor's bind. And it
        * expects a callback that takes in a DailyForecast Item, so we create a lambda and pass in
        * that type of parameter and show a message related to this
        * */
        val dailyForeCastAdapter = DailyForecastAdapter(tempDisplaySettingManager) { forecast ->
            showForecastDetails(forecast)
        }

        // Setting the adapter
        forecastList.adapter = dailyForeCastAdapter



        val weeklyForecastObserver = Observer<List<DailyForecast>>{ forecastItems ->
            // Update our List adapter
            dailyForeCastAdapter.submitList(forecastItems)
        }
        /*we observe the weeklyForecast variable by the weeklyForecastObserver
        A lifecycle owner, the main activity is passed here. Observer is also passed here
        Any time live data changes in the repository due to some reasons, the observer is updated
        which updates the ListAdapter and since we passed lifecycle observer, all of these changes
        will bound to the lifecycle of the activity*/
        forecastRepository.weeklyForecast.observe(this,weeklyForecastObserver)

        forecastRepository.loadForecast(zipcode)

        return view
    }

    // This method will navigate to the required fragment with the required parameters
    private fun showForecastDetails(forecast: DailyForecast){
        appNavigator.navigateToForecastDetails(forecast)
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
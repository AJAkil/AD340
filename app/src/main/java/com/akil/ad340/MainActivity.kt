package com.akil.ad340

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.findNavController
import com.akil.ad340.forecast.CurrentForecastFragmentDirections
import com.akil.ad340.location.LocationEntryFragmentDirections

class MainActivity : AppCompatActivity(),AppNavigator {

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    @SuppressLint("StringFormatMatches")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setting up the setting manager reference to pass it to the adapter
        tempDisplaySettingManager = TempDisplaySettingManager(this)

    }



    /*
    * This method inflates a menu to the screen
    * */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Getting a reference to the menu inflater
        // we can get direct access to a menu inflater from an activity
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        // Returning true indicates that we handled everything and we want to show the menu
        return true
    }

    // Function to handle click on the options
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle the item selection
        return when(item.itemId){
            R.id.TempDisplaySetting -> {
                // Do something if the id of the item clicked is TempDisplaySetting
                showTempDisplaySettingDialog(this, tempDisplaySettingManager)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // overriding the method to go to next fragment
    override fun navigateToCurrentForecast(zipcode: String) {
        //Now we need to add the newly created fragment to the screen upon this method call when
        // the button is pressed in the fragment

        // A reference for navigation action
        val action = LocationEntryFragmentDirections.actionLocationEntryFragmentToCurrentForecastFragment2()
        // No we make use of this action reference with an activity method
        findNavController(R.id.nav_host_fragment).navigate(action)
    }

    // overriding this to go back to fragment
    override fun navigateToLocationEntry() {
//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.fragmentContainer, LocationEntryFragment())
//            .commit()
        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
        findNavController(R.id.nav_host_fragment).navigate(action)
    }

    // method to navigate to the new fragment
    override fun navigateToForecastDetails(forecast: DailyForecast) {
        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToForecastDetailsFragment()
        findNavController(R.id.nav_host_fragment).navigate(action)
    }

}
package com.akil.ad340

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akil.ad340.details.ForecastDetailsActivity
import com.akil.ad340.forecast.CurrentForecastFragment
import com.akil.ad340.location.LocationEntryFragment
import java.util.*

class MainActivity : AppCompatActivity(),AppNavigator {

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    @SuppressLint("StringFormatMatches")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setting up the setting manager reference to pass it to the adapter
        tempDisplaySettingManager = TempDisplaySettingManager(this)


        // Fragment Creation and Addition to the screen
        // Also known as adding the fragment to the root view
        // Basically adding the fragment to the root viewgroup which is the constraint layout
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, LocationEntryFragment())
            .commit()
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
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, CurrentForecastFragment.newInstance(zipcode))
            .commit()
    }

    // overriding this to go back to fragment
    override fun navigateToLocationEntry() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, LocationEntryFragment())
            .commit()
    }

}